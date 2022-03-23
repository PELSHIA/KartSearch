package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserAvgRankBinding
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAvgRankFragment(val id: String, val type: String) : BaseFragment<FragmentUserAvgRankBinding, MatchViewModel>(R.layout.fragment_user_avg_rank) {

    override val viewModel: MatchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMatchData()
    }

    private fun getMatchData() {
        viewModel.accessIdMatchInquiry(id, type)
        viewModel.matchResponse.observe(viewLifecycleOwner, {
            setChartData(it)
        })
    }

    private fun setChartData(data: Match) {
        var avg100: Int = 0
        val rankList: MutableList<Entry> = mutableListOf()
        data.matches[0].matches.forEachIndexed { index, match ->
            if (match.player.matchRank.isNotEmpty()) {
                if (match.player.matchRank != "99") {
                    avg100 += match.player.matchRank.toInt()
                    rankList.add(Entry(index.toFloat(), match.player.matchRank.toFloat()))
                } else if (match.player.matchRank == "99" || match.player.matchRank == "") {
                    avg100 += 8
                    rankList.add(Entry(index.toFloat(), 8f))
                }
            }
        }
        setChart(rankList)
        binding.avgRank.text = String.format("최근 ${rankList.size}경기 평균순위: %.1f등", avg100.toDouble() / rankList.size.toDouble())
    }

    private fun setChart(rankList: MutableList<Entry>) {
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
}