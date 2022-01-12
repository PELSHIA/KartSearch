package com.hcraestrak.kartsearch.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentSpecificBinding
import com.hcraestrak.kartsearch.view.adapter.RankRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.RankData
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import com.hcraestrak.kartsearch.viewModel.SpecificViewModel
import java.io.File
import java.io.IOException

class SpecificFragment : Fragment() {

    private lateinit var binding: FragmentSpecificBinding
    private lateinit var recyclerViewAdapter: RankRecyclerViewAdapter
    private val viewModel: SpecificViewModel by viewModels()
    private val args: SpecificFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        bindingRecyclerView()
    }

    private fun bindingView() {
        if (args.isWin == 0) {
            bindingLose()
        } else {
            bindingWin()
        }
    }

    private fun getTrackImage(trackId: String) {
        val storageReference = FirebaseStorage.getInstance().getReference("/track/$trackId.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(requireContext()).load(bitmap).into(binding.mapImg)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(), "사진 가져오기에 실패했습니다.", Toast.LENGTH_LONG).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun bindingRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(30)
        binding.matchRankRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = RankRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        if (args.isTeamMatch) {
            setTeamData()
            binding.teamScoreLayout.visibility = View.VISIBLE
        } else {
            setSingleData()
            binding.teamScoreLayout.visibility = View.GONE
        }
    }

    private fun setSingleData() {
        val dataList = mutableListOf<RankData>()
        viewModel.specificMatchInquiry(args.matchId)
        viewModel.matchDetailObserver().observe(viewLifecycleOwner, {
            getTrackImage(it.trackId)
            for (player in it.players) {
                dataList.add(
                    RankData(
                        player.matchRank,
                        player.kart,
                        player.characterName,
                        player.matchTime,
                        args.isTeamMatch,
                        "0",
                        player.matchWin,
                        player.matchRetired
                    )
                )
            }
            recyclerViewAdapter.setData(dataList)
        })
    }

    private fun setTeamData() {
        val dataList = mutableListOf<RankData>()
        viewModel.specificTeamMatchInquiry(args.matchId)
        viewModel.matchTeamDetailObserver().observe(viewLifecycleOwner, {
            for (team in it.teams) {
                for (player in team.players) {
                    dataList.add(
                        RankData(
                            player.matchRank,
                            player.kart,
                            player.characterName,
                            player.matchTime,
                            args.isTeamMatch,
                            team.teamId,
                            player.matchWin,
                            player.matchRetired
                        )
                    )
                }
            }
            recyclerViewAdapter.setData(dataList)
        })
    }

    private fun bindingWin() {
        binding.specificOutcome.text = "승리"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
    }

    private fun bindingLose() {
        binding.specificOutcome.text = "패배"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

    }

}