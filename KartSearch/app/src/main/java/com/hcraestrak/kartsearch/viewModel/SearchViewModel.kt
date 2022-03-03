package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepository) : ViewModel() {

    private lateinit var job: Job

    private val _searchWord = MutableLiveData<List<Search>>()

    val searchWord: LiveData<List<Search>>
        get() = _searchWord

    fun getAllWord() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val words = repo.getAllWord()
            withContext(Dispatchers.Main) {
                _searchWord.postValue(words)
            }
        }
    }

    fun insertWord(search: Search) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.insertWord(search)
            }
        }
    }

    fun deleteWord(search: Search) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.deleteWord(search)
            }
        }
    }

    fun deleteAllWord() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                repo.deleteAllWord()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}