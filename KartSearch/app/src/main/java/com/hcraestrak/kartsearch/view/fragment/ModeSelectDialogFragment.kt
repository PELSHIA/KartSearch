package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentModeSelectDialogBinding
import com.hcraestrak.kartsearch.model.viewModel.UserInfoViewModel
import com.hcraestrak.kartsearch.view.adapter.ModeSelectRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.decoration.RecyclerViewDecoration
import java.util.*
import kotlin.collections.ArrayList

class ModeSelectDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentModeSelectDialogBinding
    private lateinit var recyclerViewAdapter: ModeSelectRecyclerViewAdapter
    private val userInfoViewModel: UserInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModeSelectDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingRecyclerView()
    }

    private fun bindingRecyclerView() {
        val decoration = RecyclerViewDecoration(15)
        val modeArray: Array<String> = resources.getStringArray(R.array.game_mode)
        var modeList: List<String> = modeArray.toList()
        binding.modeSelectList.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = ModeSelectRecyclerViewAdapter()
            adapter = recyclerViewAdapter
            addItemDecoration(decoration)
        }
        recyclerViewAdapter.setData(modeList)
        recyclerViewAdapter.setOnItemClickListener {
            userInfoViewModel.mode.postValue(recyclerViewAdapter.getMode())
            dismiss()
        }
    }

}