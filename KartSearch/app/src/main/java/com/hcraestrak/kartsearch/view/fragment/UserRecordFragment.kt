package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding
import com.hcraestrak.kartsearch.viewModel.ModeViewModel
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.UserInfoRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRecordFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserRecordBinding
    private lateinit var recyclerAdapter: UserInfoRecyclerViewAdapter
    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    private val matchViewModel: MatchViewModel by viewModels()
    private val viewModel: ModeViewModel by activityViewModels()
    private var gameType: String = ""
    private var isTeamMatch = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modeSelect()
        initRecyclerView()
        initMode()
        modeObserve()
    }

    private fun initMode() {
        gameType = "스피드 개인전"
        binding.userRecordTitle.text = gameType + " 전적"
        isTeamMatch(gameType)
        setData(gameType)
    }

    private fun modeSelect() {
        binding.userRecordMode.setOnClickListener {
            ModeSelectDialogFragment().show(
                parentFragmentManager, "ModeSelectDialog"
            )
        }
    }

    private fun modeObserve() {
        viewModel.mode.observe(viewLifecycleOwner, {
            Log.d("gameType", it)
            gameType = it
            binding.userRecordTitle.text = it + " 전적"
            isTeamMatch(it)
            setData(it)
        })
    }

    private fun isTeamMatch(gameType: String) {
        isTeamMatch = gameType.contains("팀전")
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        binding.userInfoRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerAdapter = UserInfoRecyclerViewAdapter()
            adapter = recyclerAdapter
            addItemDecoration(decoration)
        }

        recyclerAdapter.setOnItemClickListener {
            val matchId: String = recyclerAdapter.getMatchId()
            val isWin: Int = recyclerAdapter.getIsWin()
            findNavController().navigate(InformationFragmentDirections.actionInformationFragmentToSpecificFragment(matchId, isWin, isTeamMatch, gameType))
        }
    }

    private fun setRecyclerViewData(gameTypeId: String) {
        val dataList = mutableListOf<UserInfoData>()
        matchViewModel.accessIdMatchInquiry(id, gameTypeId)
        matchViewModel.getMatchResponseObserver().observe(viewLifecycleOwner, {
            if (it.matches.isNotEmpty()){
                binding.userRecordNone.visibility = View.GONE
                binding.userInfoRecyclerView.visibility = View.VISIBLE
                for (match in it.matches[0].matches) {
                    dataList.add(
                        UserInfoData(
                            match.playerCount,
                            match.player.matchRank,
                            match.player.kart,
                            match.trackId,
                            match.player.matchTime,
                            match.player.matchWin,
                            match.player.matchRetired,
                            match.matchId
                        )
                    )
                }
                recyclerAdapter.setData(dataList)
            } else {
                binding.userInfoRecyclerView.visibility = View.GONE
                binding.userRecordNone.visibility = View.VISIBLE
            }
        })
    }

    private fun setData(typeName: String) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (typeName == name) {
                        setRecyclerViewData(id.toString())
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }
}