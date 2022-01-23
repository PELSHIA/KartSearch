package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcraestrak.kartsearch.databinding.FragmentSearchBinding
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.view.adapter.SearchRecyclerViewAdapter
import com.hcraestrak.kartsearch.viewModel.SearchViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerViewAdapter: SearchRecyclerViewAdapter
    private val viewModel: UserViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        search()
        deleteAll()
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
        viewModel.getAccessId(nickName)
        viewModel.getObserver().observe(viewLifecycleOwner, {
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

    private fun deleteAll() {
        binding.allDelete.setOnClickListener {
            deleteAllSearchData()
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            recyclerViewAdapter = SearchRecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
        searchViewModel.getAllWord()
        searchViewModel.searchWordObserver().observe(viewLifecycleOwner, {
            val list = it.reversed()
            recyclerViewAdapter.setData(list)
        })

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
        searchViewModel.insertWord(Search(word))
    }

    private fun deleteSearchData(word: String) {
        searchViewModel.deleteWord(Search(word))
    }

    private fun deleteAllSearchData() {
        searchViewModel.deleteAllWord()
    }

}