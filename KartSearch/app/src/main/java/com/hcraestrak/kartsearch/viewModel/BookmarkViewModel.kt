package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.repo.BookmarkRepository
import com.hcraestrak.kartsearch.model.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repo: BookmarkRepository) : ViewModel() {

    private lateinit var job: Job

    private val _bookmark = MutableLiveData<List<Bookmark>>()

    val bookmark: LiveData<List<Bookmark>>
        get() = _bookmark

    fun getAllNickName() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val words = repo.getAllNickName()
            withContext(Dispatchers.Main) {
                _bookmark.postValue(words)
            }
        }
    }

    fun insertNickName(bookmark: Bookmark) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.insertNickName(bookmark)
            }
        }
    }

    fun deleteNickName(bookmark: Bookmark) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.deleteNickName(bookmark)
            }
        }
    }

    fun deleteAllNickName() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.deleteAllNickName()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}