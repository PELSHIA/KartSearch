package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.hcraestrak.kartsearch.viewModel.InformationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRecordFragment(val id: String) : BaseFragment<FragmentUserRecordBinding, MatchViewModel>(R.layout.fragment_user_record) {

    private lateinit var recyclerAdapter: UserRecordRecyclerViewAdapter
    private val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
    override val viewModel: MatchViewModel by viewModels()
    private val informationViewModel: InformationViewModel by activityViewModels()
    private val modeViewModel: ModeViewModel by activityViewModels()
    private var gameType: String = ""
    private var isTeamMatch = false
    private var page: Int = 1
    private val dataCount: Int = 15
    private val dataList = mutableListOf<UserInfoData>()
    private var isLastPage: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        modeSelect()
        initRecyclerView()
        initMode()
        modeObserve()
        scroll()
        refresh()
    }

    private fun refresh() {
        informationViewModel.isRefresh.observe(viewLifecycleOwner, {
            if (it) {
                page = 1
                isLastPage = false
                getGameTypeId(gameType)
                informationViewModel.isRefresh.postValue(false)
            }
        })
    }

    private fun initMode() {
        gameType = "스피드 개인전"
        binding.userRecordTitle.text = "스피드 개인전 전적"
        isTeamMatch(gameType)
        getGameTypeId(gameType)
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
            binding.userRecordTitle.text = "$it 전적"
            isTeamMatch(it)
            getGameTypeId(it)
        })
    }

    private fun isTeamMatch(gameType: String) {
        isTeamMatch = gameType.contains("팀전")
    }

    private fun scroll() {
        informationViewModel.isScroll.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                loadMore()
            }
        })
    }

    private fun loadMore() {
        val data = mutableListOf<UserInfoData>()
        if (dataList.size <= page * dataCount) {
            if (isLastPage) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(activity, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show()
            } else {
                for (i in page * dataCount until dataList.size) {
                    data.add(
                        UserInfoData(
                            dataList[i].playerCount,
                            dataList[i].userRank,
                            dataList[i].kart,
                            dataList[i].track,
                            dataList[i].time,
                            dataList[i].isWin,
                            dataList[i].isRetired,
                            dataList[i].matchId
                        )
                    )
                }
                recyclerAdapter.setData(data)
                binding.progressBar.visibility = View.GONE
                isLastPage = true
            }
            binding.progressBar.visibility = View.GONE
        } else {
            for (i in dataCount * page until dataCount * page + dataCount) {
                data.add(
                    UserInfoData(
                        dataList[i].playerCount,
                        dataList[i].userRank,
                        dataList[i].kart,
                        dataList[i].track,
                        dataList[i].time,
                        dataList[i].isWin,
                        dataList[i].isRetired,
                        dataList[i].matchId,
                    )
                )
            }
            Log.d("size", "RecordData.size = ${data.size}")
            recyclerAdapter.setData(data)
            page++
        }
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

    private fun setData(gameTypeId: String) {
        page = 1 // 페이지 수 초기화
        dataList.clear() // 리스트 초기화
        viewModel.accessIdMatchInquiry(id, gameTypeId)
        viewModel.matchResponse.observe(viewLifecycleOwner, {
            if (it.matches.isNotEmpty()){
                binding.userRecordNone.visibility = View.GONE
                binding.userRecordRecyclerView.visibility = View.VISIBLE
                for (match in it.matches[0].matches) { // 모든 데이터 추가
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
                setRecyclerViewData() // 데이터 초기화
            } else { // 값이 없을때
                binding.userRecordRecyclerView.visibility = View.GONE
                binding.userRecordNone.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerViewData() {
        recyclerAdapter.clearData()
        val data = mutableListOf<UserInfoData>()
        // 데이터의 수가 20이하 인 경우 페이징 처리가 필요 없다
        if (dataList.size <= dataCount) {
            recyclerAdapter.setData(dataList)
        } else {
            for (i in 0 until dataCount) {
                data.add(
                    UserInfoData(
                        dataList[i].playerCount,
                        dataList[i].userRank,
                        dataList[i].kart,
                        dataList[i].track,
                        dataList[i].time,
                        dataList[i].isWin,
                        dataList[i].isRetired,
                        dataList[i].matchId,
                    )
                )
            }
            recyclerAdapter.clearData()
            recyclerAdapter.setData(data)
        }
        page++
    }

    private fun getGameTypeId(typeName: String) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java)
                    if (typeName == name) {
                        setData(id.toString())
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