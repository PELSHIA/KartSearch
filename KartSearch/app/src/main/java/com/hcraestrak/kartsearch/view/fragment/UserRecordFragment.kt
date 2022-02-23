package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding
import com.hcraestrak.kartsearch.viewModel.ModeViewModel
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.UserRecordRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRecordFragment(val id: String) : BaseFragment<FragmentUserRecordBinding, MatchViewModel>(R.layout.fragment_user_record) {

    private lateinit var recyclerAdapter: UserRecordRecyclerViewAdapter
    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    override val viewModel: MatchViewModel by viewModels()
    private val modeViewModel: ModeViewModel by activityViewModels()
    private var gameType: String = ""
    private var isTeamMatch = false
    var title = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        modeSelect()
        initRecyclerView()
        initMode()
        modeObserve()
    }

    private fun initMode() {
        gameType = "스피드 개인전"
        title = "스피드 개인전 전적"
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
        modeViewModel.mode.observe(viewLifecycleOwner, {
            Log.d("gameType", it)
            gameType = it
            title = "$it 전적"
            isTeamMatch(it)
            setData(it)
        })
    }

    private fun isTeamMatch(gameType: String) {
        isTeamMatch = gameType.contains("팀전")
    }

    private fun initRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(40)
        val recyclerViewLayoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.userRecordRecyclerView.apply {
            layoutManager = recyclerViewLayoutManager
            recyclerAdapter = UserRecordRecyclerViewAdapter()
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
        viewModel.accessIdMatchInquiry(id, gameTypeId)
        viewModel.matchResponse.observe(viewLifecycleOwner, {
            if (it.matches.isNotEmpty()){
                binding.userRecordNone.visibility = View.GONE
                binding.userRecordRecyclerView.visibility = View.VISIBLE
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
                binding.userRecordRecyclerView.visibility = View.GONE
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