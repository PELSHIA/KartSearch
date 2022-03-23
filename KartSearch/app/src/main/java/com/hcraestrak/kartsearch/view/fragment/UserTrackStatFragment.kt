package com.hcraestrak.kartsearch.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserTrackStatBinding
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.view.adapter.data.TrackStatData
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

@AndroidEntryPoint
class UserTrackStatFragment(val id: String, val type: String) : BaseFragment<FragmentUserTrackStatBinding, MatchViewModel>(R.layout.fragment_user_track_stat) {

    override val viewModel: MatchViewModel by viewModels()
    private val trackList: MutableList<TrackStatData> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("type", type)
        getMatchData()
    }

    private fun getMatchData() {
        viewModel.accessIdMatchInquiry(id, type)
        viewModel.matchResponse.observe(viewLifecycleOwner, {
            setTrackData(it)
        })
    }

    private fun setTrackData(data: Match) {
        data.matches[0].matches.forEach { match ->
            val isExistTrack = trackList.any { it.track == match.trackId }
            if (isExistTrack) {
                val condition = { data:TrackStatData -> data.track == match.trackId }
                val position = trackList.indexOf(trackList.find(condition))
                trackList[position].number = (trackList[position].number.toInt() + 1).toString()
                when (match.player.matchRank) {
                    "99" -> {
                        trackList[position].avg = (trackList[position].avg.toInt()+ 9).toString()
                    }
                    "" -> {
                        trackList[position].avg = (trackList[position].avg.toInt() + 9).toString()
                    }
                    else -> {
                        trackList[position].avg = (trackList[position].avg.toInt() + match.player.matchRank.toInt()).toString()
                        trackList[position].win = (trackList[position].win.toInt() + 1).toString()
                    }
                }
                if (match.player.matchTime != "") {
                    if (trackList[position].time > match.player.matchTime) {
                        trackList[position].time = match.player.matchTime
                    }
                }
            } else {
                trackList.add(
                    TrackStatData(
                        match.trackId,
                        "1",
                        if (match.player.matchWin == "1") "1" else "0",
                        if (match.player.matchRank == "99" || match.player.matchRank == "") "8" else match.player.matchRank,
                        if (match.player.matchTime == "") "999999" else match.player.matchTime
                    )
                )
            }
        }
        Log.d("trackList", trackList.toString())
        setBestTrack()
    }

    private fun setBestTrack() {
        var bestWin: Int = trackList[0].win.toInt()
        var bestWinNumber: Int = trackList[0].number.toInt()
        var bestRank: Int = trackList[0].avg.toInt()
        var bestRankNumber: Int = trackList[0].number.toInt()
        var bestPlay: Int = trackList[0].number.toInt()

        var bestWinTrack: String = trackList[0].track
        var bestRankTrack: String = trackList[0].track
        var bestPlayTrack: String = trackList[0].track

        trackList.forEach { data ->
            Log.d("data", data.toString())
            if (data.win.toInt() > bestWin) { // 최고 승률
                bestWinNumber = data.number.toInt()
                bestWin = data.win.toInt()
                bestWinTrack = data.track
            }
            if (data.avg.toDouble() / data.number.toDouble() < bestRank.toDouble() / bestRankNumber.toDouble()) { // 평균 순위 1등
                bestRankNumber = data.number.toInt()
                bestRank = data.avg.toInt()
                bestRankTrack = data.track
            }
            if (data.number.toInt() > bestPlay) { // 제일 많은 플레이 횟수
                bestPlay = data.number.toInt()
                bestPlayTrack = data.track
            }
        }
        bindingView("win", bestWinTrack, bestWin, bestWinNumber)
        bindingView("rank", bestRankTrack, bestRank, bestRankNumber)
        bindingView("play", bestPlayTrack, bestPlay, 0)
    }

    private fun bindingView(type: String, trackId: String, trackStats: Int, number: Int) {
        Log.d("bindingView", "$type, $trackId, $trackStats, $number")
        when (type) {
            "win" -> {
                setImage(binding.trackWinStatsImg, trackId)
                setTrackName(binding.trackWinStatsName, trackId)
                binding.trackWinStats.text = (trackStats.toDouble() / number.toDouble() * 100.0).roundToInt().toString() + "%"
            }
            "rank" -> {
                setImage(binding.trackRankStatsImg, trackId)
                setTrackName(binding.trackRankStatsName, trackId)
                binding.trackRankStats.text =  String.format("%.1f등", trackStats.toDouble() / number.toDouble())
            }
            "play" -> {
                setImage(binding.trackPlayStatsImg, trackId)
                setTrackName(binding.trackPlayStatsName, trackId)
                binding.trackPlayStats.text = trackStats.toString() + "회"
            }
        }
    }

    private fun setImage(view: ImageView, src: String){
        val storageReference = FirebaseStorage.getInstance().getReference("/track/$src.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(view.context).load(bitmap).into(view)
                }.addOnFailureListener{
                    Glide.with(view.context).load(R.drawable.unknowntrack).into(view)
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setTrackName(view: TextView, trackId: String) {
        val database: DatabaseReference = Firebase.database("https://kartmap.firebaseio.com/").reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java).toString()
                    if (id == trackId) {
                        view.text = name
                        break
                    }
                    view.text = "알 수 없는 트랙"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }
}