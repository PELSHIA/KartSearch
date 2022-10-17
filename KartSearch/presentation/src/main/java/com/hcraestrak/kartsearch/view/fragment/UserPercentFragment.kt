package com.hcraestrak.kartsearch.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.hcraestrak.domain.model.remote.Match
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserPercentBinding
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPercentFragment(val id: String, val type: String) : BaseFragment<FragmentUserPercentBinding, MatchViewModel>(R.layout.fragment_user_percent) {

    override val viewModel: MatchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMatchData()
    }

    private fun getMatchData() {
        viewModel.accessIdMatchInquiry(id, type)
        viewModel.matchResponse.observe(viewLifecycleOwner) {
            if (it.matches.isNotEmpty()) {
                setChartData(it)
            }
        }
    }

    private fun setChartData(data: Match) {
        var win: Int = 0
        var completion: Int = 0
        data.matches[0].matches.forEach { match ->
            if (match.player.matchWin == "1") {
                win++
            }
            if (match.player.matchRetired == "0" || match.player.matchRetired == "") {
                completion++
            }
        }
        setWinChart(win)
        setCompletionChart(completion)
    }

    private fun setWinChart(win: Int) {
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

    private fun setCompletionChart(completion: Int) {
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
}