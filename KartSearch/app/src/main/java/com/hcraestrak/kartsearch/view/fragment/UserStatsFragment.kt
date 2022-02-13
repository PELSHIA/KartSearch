package com.hcraestrak.kartsearch.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserStatsBinding
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.view.adapter.TrackStatRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.TrackStatData
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.viewModel.ModeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStatsFragment(val id: String) : BaseFragment<FragmentUserStatsBinding, MatchViewModel>(R.layout.fragment_user_stats) {

    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    private val modeViewModel: ModeViewModel by activityViewModels()
    override val viewModel: MatchViewModel by viewModels()
    private lateinit var recyclerViewAdapter: TrackStatRecyclerViewAdapter
    var title: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        initData()
        modeSelect()
        modeObserve()
    }

    private fun modeSelect() {
        binding.userRecordMode.setOnClickListener {
            ModeSelectDialogFragment().show(
                parentFragmentManager, "ModeSelectDialog"
            )
        }
    }

    private fun initData() {
        title = "스피드 개인전 전적"
        getGameTypeId("스피드 개인전")
    }

    private fun modeObserve() {
        modeViewModel.mode.observe(viewLifecycleOwner, {
            title = "$it 전적"
            getGameTypeId(it)
        })
    }

    private fun getGameTypeId(typeName: String) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (typeName == name) {
                        getData(id.toString())
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }

    private fun getData(typeId: String) {
        viewModel.accessIdMatchInquiry(id, matchType=typeId, limit=100)
        viewModel.getMatchResponseObserver().observe(viewLifecycleOwner, {
            if (it.matches.isNotEmpty()) {
                binding.statsLayout.visibility = View.VISIBLE
                binding.userStatsNone.visibility = View.GONE
                setChartDataSetting(it)
            } else {
                binding.userStatsNone.visibility = View.VISIBLE
                binding.statsLayout.visibility = View.GONE
            }
        })
    }

    private fun setChartDataSetting(data: Match) {
        var win: Int = 0
        var completion: Int = 0
        var avg100: Int = 0
        val rankList: MutableList<Entry> = mutableListOf()
        val trackList: MutableList<TrackStatData> = mutableListOf()
        data.matches[0].matches.forEachIndexed { index, match ->
            val isExistTrack = trackList.any { it.track == match.trackId }
            if (match.player.matchWin == "1") {
                win++
            }
            if (match.player.matchRetired == "0") {
                completion++
            }
            if (match.player.matchRank.isNotEmpty()) {
                if (match.player.matchRank != "99") {
                    avg100 += match.player.matchRank.toInt()
                    rankList.add(Entry(index.toFloat(), match.player.matchRank.toFloat()))
                } else if (match.player.matchRank == "99" || match.player.matchRank == "") {
                    rankList.add(Entry(index.toFloat(), 8f))
                    avg100 += 8
                }
            }
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
        binding.avgRank.text = String.format("최근 100경기 평균순위: %.1f등", avg100.toDouble() / 100.0)
        winChart(win)
        completionChart(completion)
        rankChart(rankList)
        trackRecyclerView(trackList)
    }

    private fun winChart(win: Int) {
        val dataList: MutableList<PieEntry> = mutableListOf()
        val colorList: MutableList<Int> = mutableListOf(Color.parseColor("#7CA8FF"), Color.parseColor("#D4D4D4"))

        dataList.add(PieEntry(win.toFloat()))
        dataList.add(PieEntry((100 - win).toFloat()))

        val pieDataset: PieDataSet = PieDataSet(dataList, "")
        pieDataset.apply {
            colors = colorList
        }

        val pieData: PieData = PieData(pieDataset)
        binding.winStatChart.apply {
            data = pieData
            description.isEnabled = false
            legend.isEnabled = false
            centerText = "$win%"
            animateY(800, Easing.EaseInOutQuad)
            animate()
            setTouchEnabled(false)
            invalidate()
        }

    }

    private fun completionChart(completion: Int) {
        val dataList: MutableList<PieEntry> = mutableListOf()
        val colorList: MutableList<Int> = mutableListOf(Color.parseColor("#AEFF6F"), Color.parseColor("#FF8484"))

        dataList.add(PieEntry(completion.toFloat()))
        dataList.add(PieEntry((100 - completion).toFloat()))

        val pieDataset: PieDataSet = PieDataSet(dataList, "")
        pieDataset.apply {
            colors = colorList
        }

        val pieData: PieData = PieData(pieDataset)
        binding.completionStatChart.apply {
            data = pieData
            description.isEnabled = false
            legend.isEnabled = false
            centerText = "$completion%"
            animateY(800, Easing.EaseInOutQuad)
            animate()
            setTouchEnabled(false)
            invalidate()
        }
    }

    private fun rankChart(rankList: MutableList<Entry>) {
        val lineDataSet: LineDataSet = LineDataSet(rankList, "")
        val dataSets: MutableList<ILineDataSet> = mutableListOf()
        dataSets.add(lineDataSet)
        val lineData: LineData = LineData(dataSets)

        binding.avgRankChart.apply {
            data = lineData
            legend.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.axisMinimum = 1f
            axisLeft.axisMaximum = 8f
            axisRight.apply {
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
            }
            invalidate()
        }
    }

    private fun trackRecyclerView(list: List<TrackStatData>) {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(20)
        val recyclerViewLayoutManager = object : LinearLayoutManager(binding.trackStatRecyclerView.context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.trackStatRecyclerView.apply {
            layoutManager = recyclerViewLayoutManager
            recyclerViewAdapter = TrackStatRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        recyclerViewAdapter.setData(list)
    }
}