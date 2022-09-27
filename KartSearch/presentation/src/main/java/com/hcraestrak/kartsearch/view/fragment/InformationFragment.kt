package com.hcraestrak.kartsearch.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentInformationBinding
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.view.adapter.InformationViewPagerAdapter
import com.hcraestrak.kartsearch.view.adapter.UserRecordRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.UserInfoData
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import com.hcraestrak.kartsearch.viewModel.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding, MatchViewModel>(R.layout.fragment_information) {

    override val viewModel: MatchViewModel by activityViewModels()
    private val bookMark: BookmarkViewModel by viewModels()
    private val modeViewModel: ModeViewModel by activityViewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    private lateinit var recyclerAdapter: UserRecordRecyclerViewAdapter

    private val args: InformationFragmentArgs by navArgs()
    private val dataList = mutableListOf<UserInfoData>()
    private lateinit var storageReference: StorageReference
    private var gameType: String = ""
    private var isTeamMatch = false
    private var page: Int = 1
    private val dataCount: Int = 15
    private var isLastPage: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mode = modeViewModel
        bindingToolbar()
        initRecyclerView()
        scroll()
        modeSelect()
        modeObserve()
        goToUp()
        bookmark()
        refresh()
    }

    private fun modeSelect() {
        binding.userMode.setOnClickListener {
            ModeSelectDialogFragment().show(
                parentFragmentManager, "ModeSelectDialog"
            )
        }
    }

    private fun modeObserve() {
        modeViewModel.mode.observe(viewLifecycleOwner, {
            Log.d("gameType", it)
            gameType = it
            isTeamMatch(it)
            getGameTypeId(it)
        })
    }

    private fun isTeamMatch(gameType: String) {
        isTeamMatch = gameType.contains("팀전")
    }

    private fun bindingToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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

    private fun setData(type: String) {
        viewModel.accessIdMatchInquiry(args.accessId, type)
        setViewPager(type)
        viewModel.matchResponse.observe(viewLifecycleOwner, { match ->
            binding.toolbarTitle.text = match.nickName + " 님의 전적"
            binding.userNickName.text = match.nickName
            initBookmark(match.nickName)
            if (match.matches.isNotEmpty()){
                binding.userStatsViewPager.visibility = View.VISIBLE
                binding.userRecordRecyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.userRecordNone.visibility = View.GONE
                setRecyclerViewData(match)
                setUserInfo(match.nickName)
                getImage("character", match.matches[0].matches[0].character, binding.userProfileImg) // 대표 캐릭터 이미지
                getLicenseImage(match.matches[0].matches[0].player.rankinggrade2) // 라이센스 이미지
            } else {
                binding.userStatsViewPager.visibility = View.GONE
                binding.userRecordRecyclerView.visibility = View.GONE
                binding.userRecordNone.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun initBookmark(nickName: String) {
        bookMark.isExists(nickName)
        bookMark.isExists.observe(viewLifecycleOwner, { // 즐겨찾기 초기 설정
            if (it) {
                Glide.with(binding.userBookmark.context).load(R.drawable.ic_star).into(binding.userBookmark)
            } else {
                Glide.with(binding.userBookmark.context).load(R.drawable.ic_star_border).into(binding.userBookmark)
            }
        })
    }

    private fun setRecyclerViewData(data: Match) {
        page = 1
        isLastPage = false
        dataList.clear()
        if (data.matches.isNotEmpty()){
            binding.userRecordRecyclerView.visibility = View.VISIBLE
            for (match in data.matches[0].matches) { // 모든 데이터 추가
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
            setInitRecyclerViewData() // 데이터 초기화
        } else { // 값이 없을때
            binding.userRecordRecyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setInitRecyclerViewData() {
        recyclerAdapter.clearData()
        val data = mutableListOf<UserInfoData>()
        // 데이터의 수가 20이하 인 경우 페이징 처리가 필요 없다
        if (dataList.size <= dataCount) {
            recyclerAdapter.setData(dataList)
            recyclerAdapter.notifyItemInserted(dataList.size)
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
            recyclerAdapter.setData(data)
            recyclerAdapter.notifyItemInserted(dataCount)
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun loadMore() {
        val data = mutableListOf<UserInfoData>()
        if (dataList.size - dataCount * page <= dataCount) {
            if (isLastPage) {
                binding.progressBar.visibility = View.GONE
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
                recyclerAdapter.notifyItemChanged(dataList.size)
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
            recyclerAdapter.setData(data)
            recyclerAdapter.notifyItemChanged(dataCount * page + dataCount)
            page++
        }
    }

    private fun setUserInfo(nickName: String) {
        userInfoViewModel.getProfileData(nickName)
        userInfoViewModel.getRecordData(nickName)
        userInfoViewModel.infoData.observe(viewLifecycleOwner, {
            Glide.with(binding.userLevelImg.context).load(it.levelImg).into(binding.userLevelImg)
            binding.userClub.text = if (it.club != "가입된클럽이없습니다") it.club else "없음"
        })
        userInfoViewModel.recordData.observe(viewLifecycleOwner, {
            val playTime = it.playTime.replace("분", "")
            binding.userPlayTime.text = (playTime.toInt() / 60).toString() + "시간 플레이"
            binding.userStartDate.text = it.startTime.substring(0, 10) + " 부터"
        })
    }

    private fun getImage(type: String, id: String, view: ImageView) {
        storageReference = FirebaseStorage.getInstance().getReference("/$type/$id.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(view.context).load(bitmap).into(view)
                }.addOnFailureListener{
                    if (type == "character") {
                        Glide.with(view.context).load(R.drawable.unknowncharacter).into(view)
                    }
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getLicenseImage(license: String) { // 라이센스 판별
        when(license) {
            "1" -> getImage("License", "chobo", binding.userLicense)
            "2" -> getImage("License", "Rookie", binding.userLicense)
            "3" -> getImage("License", "L3", binding.userLicense)
            "4" -> getImage("License", "L2", binding.userLicense)
            "5" -> getImage("License", "L1", binding.userLicense)
            "6" -> getImage("License", "PRO", binding.userLicense)
        }
    }

    private fun setViewPager(type: String) {
        binding.userStatsViewPager.adapter = InformationViewPagerAdapter(requireActivity(), args.accessId, type)
    }

    private fun scroll() {
        binding.scrollView.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == binding.scrollView.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadMore()
            }
        }
    }

    private fun goToUp() {
        binding.goToUp.setOnClickListener {
            binding.scrollView.fullScroll(0)
        }
    }

    private fun bookmark() {
        binding.userBookmark.setOnClickListener {
            viewModel.matchResponse.observe(viewLifecycleOwner, { match ->
                bookMark.isExists(match.nickName)
                bookMark.isExists.observe(viewLifecycleOwner, {
                    if (it) {
                        bookMark.deleteNickName(Bookmark(match.nickName))
                        Glide.with(binding.userBookmark.context).load(R.drawable.ic_star_border).into(binding.userBookmark)
                    } else {
                        bookMark.insertNickName(Bookmark(match.nickName))
                        Glide.with(binding.userBookmark.context).load(R.drawable.ic_star).into(binding.userBookmark)
                    }
                })
            })
        }
    }

    private fun refresh() {
        binding.refreshLayout.setProgressViewOffset(false, 0, 200) // Progress location
        binding.refreshLayout.setOnRefreshListener {
            page = 1
            isLastPage = false
            getGameTypeId(gameType)
        }
    }

    private fun getGameTypeId(typeName: String) {
        val database: DatabaseReference = Firebase.database("https://gametype.firebaseio.com/").reference
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