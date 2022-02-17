package com.hcraestrak.kartsearch.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentSpecificBinding
import com.hcraestrak.kartsearch.view.adapter.RankRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.data.RankData
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.viewModel.SpecificViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class SpecificFragment : BaseFragment<FragmentSpecificBinding, SpecificViewModel>(R.layout.fragment_specific) {

    private lateinit var recyclerViewAdapter: RankRecyclerViewAdapter
    override val viewModel: SpecificViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val args: SpecificFragmentArgs by navArgs()
    private val dataList = mutableListOf<RankData>()
    var gameType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecificBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        bindingToolbar()
        bindingView()
        bindingRecyclerView()
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

    private fun bindingView() {
        gameType = args.gameType
        if (args.isWin == 0) {
            bindingLose()
        } else {
            bindingWin()
        }
    }

    private fun getTrackImage(trackId: String) {
        val storageReference = FirebaseStorage.getInstance().getReference("/track/$trackId.png")
        try {
            val localFile: File = File.createTempFile("tempfile", ".png")
            storageReference.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Glide.with(binding.mapImg.context).load(bitmap).into(binding.mapImg)
                }.addOnFailureListener{
                    Glide.with(binding.mapImg.context).load(R.drawable.unknowntrack).into(binding.mapImg)
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun bindingRecyclerView() {
        val decoration: RecyclerViewDecoration = RecyclerViewDecoration(30)
        binding.matchRankRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerViewAdapter = RankRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        if (args.isTeamMatch) {
            setTeamData()
            if (args.gameType.contains("아이템")) {
                binding.teamScoreLayout.visibility = View.GONE
            } else {
                binding.teamScoreLayout.visibility = View.VISIBLE
            }
        } else {
            setSingleData()
            binding.teamScoreLayout.visibility = View.GONE
        }

        recyclerViewAdapter.setOnItemClickListener {
            if (it == 1) {
                userViewModel.getAccessId(recyclerViewAdapter.getNickName())
                userViewModel.userInfoLiveData.observe(viewLifecycleOwner, { userInfo ->
                    if (userInfo != null) {
                        findNavController().navigate(SpecificFragmentDirections.actionSpecificFragmentToInformationFragment(userInfo.accessId))
                    } else {
                        findNavController().navigate(SpecificFragmentDirections.actionSpecificFragmentToErrorFragment())
                    }
                })
            }
        }
    }

    private fun getTrackName(trackId: String) {
        val database: DatabaseReference = Firebase.database("https://kartmap.firebaseio.com/").reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val id = postSnapshot.child("id").getValue(String::class.java)
                    val name = postSnapshot.child("name").getValue(String::class.java).toString()
                    if (id == trackId) {
                        binding.mapName.text = name
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "${error.code}: ${error.message}")
            }
        })
    }

    private fun setSingleData() {
        viewModel.specificMatchInquiry(args.matchId)
        viewModel.matchDetail.observe(viewLifecycleOwner, { match ->
            getTrackImage(match.trackId)
            getTrackName(match.trackId)
            for (player in match.players) {
                dataList.add(
                    RankData(
                        isForceQuit(player.matchRank),
                        player.kart,
                        player.characterName,
                        player.matchTime,
                        args.isTeamMatch,
                        "0",
                        player.matchWin,
                        player.matchRetired
                    )
                )
            }
            dataList.sortBy { it.rank }
            recyclerViewAdapter.setData(dataList)
        })
    }

    private fun setTeamData() {
        viewModel.specificTeamMatchInquiry(args.matchId)
        viewModel.matchTeamDetail.observe(viewLifecycleOwner, { match ->
            getTrackImage(match.trackId)
            getTrackName(match.trackId)
            for (team in match.teams) {
                for (player in team.players) {
                    dataList.add(
                        RankData(
                            isForceQuit(player.matchRank),
                            player.kart,
                            player.characterName,
                            player.matchTime,
                            args.isTeamMatch,
                            team.teamId,
                            player.matchWin,
                            player.matchRetired
                        )
                    )
                }
            }
            dataList.sortBy { it.rank }
            setTeamScore()
            recyclerViewAdapter.setData(dataList)
        })
    }

    private fun isForceQuit(rank: String): String {
        return if (rank == "") "99" else rank
    }

    private fun setTeamScore() {
        var rank = 1
        var blueScore = 0 // 2
        var redScore = 0 // 1
        val scoreList = listOf<Int>(10, 8, 6, 5, 4, 3, 2, 1) // 팀전 점수
        dataList.forEach {
            if (it.isRetire == "0") {
                if (it.teamId == "1") {
                    redScore += scoreList[rank - 1]
                    rank++
                } else if (it.teamId == "2") {
                    blueScore += scoreList[rank - 1]
                    rank++
                }
            } else {
                rank++
            }
        }
        binding.blueScore.text = "Blue " + blueScore.toString()
        binding.redScore.text = redScore.toString() + " Red"
    }

    private fun bindingWin() {
        binding.specificOutcome.text = "승리"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
    }

    private fun bindingLose() {
        binding.specificOutcome.text = "패배"
        binding.outcomeColor.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

    }

}