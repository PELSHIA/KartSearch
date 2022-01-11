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
import com.hcraestrak.kartsearch.viewModel.UserInfoViewModel
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.UserInfoRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration

class UserRecordFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserRecordBinding
    private lateinit var recyclerAdapter: UserInfoRecyclerViewAdapter
    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    private val matchViewModel: MatchViewModel by viewModels()
    private val viewModel: UserInfoViewModel by activityViewModels()
    private var typeId: String = ""

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
        viewModel.mode.observe(viewLifecycleOwner, {
            Log.d("gameType", it)
            binding.userRecordTitle.text = it + "전적"
            setData(it)
        })
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
            findNavController().navigate(InformationFragmentDirections.actionInformationFragmentToSpecificFragment(matchId, isWin))
        }
    }

    private fun setData(type: String) {
        val dataList = mutableListOf<UserInfoData>()
        matchViewModel.accessIdMatchInquiryWithMatchType(id, getGameTypeWithFirebase(type))
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
                        match.player.matchRetired,
                        match.matchId
                    )
                )
            }
            recyclerAdapter.setData(dataList)
        })
    }

    private fun getGameTypeWithFirebase(typeName: String): String {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (typeName == name) {
                        typeId = id.toString()
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
        return typeId
    }
}