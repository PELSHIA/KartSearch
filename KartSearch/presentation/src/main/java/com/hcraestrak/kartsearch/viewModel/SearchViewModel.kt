package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.domain.useCase.local.search.DeleteAllUseCase
import com.hcraestrak.domain.useCase.local.search.DeleteWordUseCase
import com.hcraestrak.domain.useCase.local.search.GetAllUseCase
import com.hcraestrak.domain.useCase.local.search.InsertWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllUseCase: GetAllUseCase,
    private val insertWordUseCase: InsertWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val deleteAllUseCase: DeleteAllUseCase
) : ViewModel() {

    private val _searchWord = MutableLiveData<List<Search>>()

    val searchWord: LiveData<List<Search>>
        get() = _searchWord

    fun getAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            val wordList = getAllUseCase.execute()
            _searchWord.postValue(wordList)
        }
    }

    fun insertWord(search: Search) {
        viewModelScope.launch(Dispatchers.IO) {
            insertWordUseCase.execute(search)
        }
    }

    fun deleteWord(search: Search) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteWordUseCase.execute(search)
        }
    }

    fun deleteAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllUseCase.execute()
        }
    }

}