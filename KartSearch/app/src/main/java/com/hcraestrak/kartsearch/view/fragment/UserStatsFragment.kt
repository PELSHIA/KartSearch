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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hcraestrak.kartsearch.databinding.FragmentUserStatsBinding
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.viewModel.ModeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserStatsFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserStatsBinding
    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    private val modeViewModel: ModeViewModel by activityViewModels()
    private val viewModel: MatchViewModel by viewModels()
    private var gameTypeId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun modeObserve() {
        modeViewModel.mode.observe(viewLifecycleOwner, {
            binding.userStatsTitle.text = it + " 전적"
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
            pieChartSetting(it)
        })
    }

    private fun pieChartSetting(data: Match) {
        var win: Int = 0
        var completion: Int = 0
        data.matches[0].matches.forEach {
            if (it.player.matchWin == "1") {
                win++
            }
            if (it.player.matchRetired == "0") {
                completion++
            }
        }
        winChart(win)
        completionChart(completion)
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
        }
    }
}