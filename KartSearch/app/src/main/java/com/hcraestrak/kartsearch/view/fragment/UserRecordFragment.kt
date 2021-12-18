package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding
import com.hcraestrak.kartsearch.model.viewModel.FirebaseViewModel
import com.hcraestrak.kartsearch.model.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.view.adapter.UserInfoRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration

class UserRecordFragment(val id: String) : Fragment() {

    private lateinit var binding: FragmentUserRecordBinding
    private lateinit var recyclerAdapter: UserInfoRecyclerViewAdapter
    private val matchViewModel: MatchViewModel by viewModels()
    private lateinit var database: DatabaseReference
    private val fireBaseViewModel: FirebaseViewModel by viewModels()
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
                setData(binding.userRecordSpinner.selectedItem.toString())
                false
            } else {
                stateSingle(resources.getColor(R.color.gray), 0)
                stateTeam(resources.getColor(R.color.light_blue), R.drawable.background_on)
                bindingSpinner()
                bindingTitle()
                setData(binding.userRecordSpinner.selectedItem.toString())
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
                setData(binding.userRecordSpinner.selectedItem.toString())
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
        }
    }

    private fun setData(type: String) {
        val dataList = mutableListOf<UserInfoData>()
        val gameType: String = getGameType(type)
        matchViewModel.accessIdMatchInquiryWithMatchType(id, gameType)
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

    private fun getGameType(type: String): String {
        val result: String
        if (isClicked) {
            when (type) {
                "스피드" -> {
                    Log.d("gameType", getGameTypeWithFirebase("스피드 개인전"))
                    return getGameTypeWithFirebase("스피드 개인전")
                }
                "아이템" -> {
                    return getGameTypeWithFirebase("아이템 개인전")
                }
                "무한부스터" -> {
                    return getGameTypeWithFirebase("무한부스터 개인전")
                }
//                "등급전" -> {
//                }
            }
        } else {
            when (type) {
                "스피드" -> {
                    return getGameTypeWithFirebase("스피드 팀전")
                }
                "아이템" -> {
                    return getGameTypeWithFirebase("아이템 팀전")
                }
//                "무한부스터" -> {
//
//                }
//                "등급전" -> {
//
//                }
                "스피드 클럽전" -> {
                    return getGameTypeWithFirebase("클럽 스피드 팀전")
                }
                "아이템 클럽전" -> {
                    return getGameTypeWithFirebase("클럽 아이템 팀전")
                }
                "팀 배틀" -> {
                    return getGameTypeWithFirebase("아이템 팀 배틀모드")
                }
            }
        }
        return ""
    }

    private fun getGameTypeWithFirebase(gameTypeName: String): String {
        var data: String = ""
        database = Firebase.database("https://gametype.firebaseio.com/").reference
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (name == gameTypeName) {
                        data = id.toString()
                        return
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
        return data
    }
}