package com.hcraestrak.kartsearch.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentSearchBinding
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.view.adapter.SearchRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.SearchViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    private lateinit var recyclerViewAdapter: SearchRecyclerViewAdapter
    private val userViewModel: UserViewModel by viewModels()
    override val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWord()
        initRecyclerView()
        search()
    }

    private fun search() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearch(binding.searchEditText.text.toString())
            }
            false
        }
    }

    private fun getSearch(nickName: String) {
        userViewModel.getAccessId(nickName)
        userViewModel.userInfoLiveData.observe(viewLifecycleOwner, {
            navigate(it)
        })
    }

    private fun navigate(userInfo: UserInfo?) {
        if (userInfo == null) {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToErrorFragment())
        } else {
            setSearchData(userInfo.name)
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToInformationFragment(
                    userInfo.accessId
                )
            )
        }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = SearchRecyclerViewAdapter()
        recyclerViewAdapter.setOnItemClickListener {
            val word: String = recyclerViewAdapter.getWord()
            when(it) {
                1 -> {
                    deleteSearchData(word)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                2 -> {
                    getSearch(word)
                }
            }
        }
    }

    private fun setSearchData(word: String) {
        viewModel.insertWord(Search(word))
    }

    private fun deleteSearchData(word: String) {
        viewModel.deleteWord(Search(word))
    }

}