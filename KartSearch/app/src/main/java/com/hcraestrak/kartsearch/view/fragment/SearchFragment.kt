package com.hcraestrak.kartsearch.view.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentSearchBinding
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.view.adapter.BookmarkRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.adapter.SearchRecyclerViewAdapter
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.BookmarkViewModel
import com.hcraestrak.kartsearch.viewModel.SearchViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {

    private lateinit var recyclerViewAdapter: SearchRecyclerViewAdapter
    private lateinit var bookmarkRecyclerViewAdapter: BookmarkRecyclerViewAdapter
    private val userViewModel: UserViewModel by viewModels()
    override val viewModel: SearchViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private var mode: Boolean = true // true 최근검색어 false 즐겨찾기

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllWord()
        initRecyclerView()
        search()
        changeMode()
        allDelete()
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
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            recyclerViewAdapter = SearchRecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
        binding.bookmarkRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            bookmarkRecyclerViewAdapter = BookmarkRecyclerViewAdapter()
            adapter = bookmarkRecyclerViewAdapter
        }
        setData()
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

        bookmarkRecyclerViewAdapter.setOnItemClickListener {
            val nickName: String = bookmarkRecyclerViewAdapter.getWord()
            when (it) {
                1 -> {
                    bookmarkViewModel.deleteNickName(Bookmark(nickName))
                    bookmarkRecyclerViewAdapter.notifyDataSetChanged()
                }
                2 -> {
                    getSearch(nickName)
                }
            }
        }
    }

    private fun setData() {
        viewModel.getAllWord()
        viewModel.searchWord.observe(viewLifecycleOwner, {
            val list = it.reversed()
            recyclerViewAdapter.setData(list)
        })

        bookmarkViewModel.getAllNickName()
        bookmarkViewModel.bookmark.observe(viewLifecycleOwner, {
            val list = it.reversed()
            bookmarkRecyclerViewAdapter.setData(list)
        })
    }

    private fun changeMode() {
        if (mode) {
            recentSearch()
        } else {
            bookMarkMode()
        }
        
        binding.bookmark.setOnClickListener {
            mode = false
            bookMarkMode()
        }
        binding.recentSearch.setOnClickListener {
            mode = true
            recentSearch()
        }
    }

    private fun bookMarkMode() {
        binding.searchRecyclerView.visibility = View.GONE
        binding.bookmarkRecyclerView.visibility = View.VISIBLE
        binding.bookmark.setTextColor(ContextCompat.getColor(binding.bookmark.context, R.color.light_blue))
        binding.recentSearch.setTextColor(ContextCompat.getColor(binding.recentSearch.context, R.color.black))
    }

    private fun recentSearch() {
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.bookmarkRecyclerView.visibility = View.GONE
        binding.bookmark.setTextColor(ContextCompat.getColor(binding.bookmark.context, R.color.black))
        binding.recentSearch.setTextColor(ContextCompat.getColor(binding.recentSearch.context, R.color.light_blue))
    }

    private fun allDelete() {
        binding.allDelete.setOnClickListener {
            if (mode) {
                viewModel.deleteAllWord()
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                bookmarkViewModel.deleteAllNickName()
                bookmarkRecyclerViewAdapter.notifyDataSetChanged()
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