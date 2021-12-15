package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding
import com.hcraestrak.kartsearch.model.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.UserInfoRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration

class UserRecordFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserRecordBinding
    private lateinit var recyclerAdapter: UserInfoRecyclerViewAdapter
    private val matchViewModel: MatchViewModel by viewModels()
    private var isClicked: Boolean = true // true 개인전 false 팀전

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingStateButton()
        bindingSpinner()
        bindingTitle()
        initRecyclerView()
    }

    private fun bindingStateButton() {
        binding.userInfoStateLayout.setOnClickListener {
            isClicked = if (isClicked) {
                stateSingle(resources.getColor(R.color.light_blue), R.drawable.background_on)
                stateTeam(resources.getColor(R.color.gray), 0)
                bindingSpinner()
                bindingTitle()
                false
            } else {
                stateSingle(resources.getColor(R.color.gray), 0)
                stateTeam(resources.getColor(R.color.light_blue), R.drawable.background_on)
                bindingSpinner()
                bindingTitle()
                true
            }
        }
    }

    private fun stateTeam(color: Int, backGround: Int) {
        binding.userInfoStateTeam.setTextColor(color)
        binding.userInfoStateTeam.setBackgroundResource(backGround)
    }

    private fun stateSingle(color: Int, backGround: Int) {
        binding.userInfoStateSingle.setTextColor(color)
        binding.userInfoStateSingle.setBackgroundResource(backGround)
    }

    private fun bindingSpinner() {
        if (isClicked) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.single_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                binding.userRecordSpinner.adapter = adapter
            }
        } else {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.team_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                binding.userRecordSpinner.adapter = adapter
            }
        }

        binding.userRecordSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bindingTitle()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun bindingTitle() {
        if (isClicked) {
            binding.userRecordTitle.text = binding.userRecordSpinner.selectedItem.toString() + " 팀전 전적"
        } else {
            binding.userRecordTitle.text = binding.userRecordSpinner.selectedItem.toString() + " 개인전 전적"
        }
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.userInfoRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerAdapter = UserInfoRecyclerViewAdapter()
            adapter = recyclerAdapter
            addItemDecoration(decoration)
            setData()
        }
    }

    private fun setData() {
        val dataList = mutableListOf<UserInfoData>()
        matchViewModel.accessIdMatchInquiryWithMatchType(id, "effd66758144a29868663aa50e85d3d95c5bc0147d7fdb9802691c2087f3416e")
        matchViewModel.getMatchResponseObserver().observe(viewLifecycleOwner, {
            for (match in it.matches[0].matches) {
                dataList.add(
                    UserInfoData(
                        match.playerCount,
                        match.player.matchRank,
                        match.player.kart,
                        match.trackId,
                        match.player.matchTime,
                        match.player.matchWin,
                        match.player.matchRetired
                    )
                )
            }
            recyclerAdapter.setData(dataList)
        })
    }
}