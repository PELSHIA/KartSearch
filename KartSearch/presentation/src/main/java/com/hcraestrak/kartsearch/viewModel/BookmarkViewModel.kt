package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.useCase.local.bookmark.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getAllNickNamesUseCase: GetAllNickNamesUseCase,
    private val insertNickNamesUseCase: InsertNickNamesUseCase,
    private val deleteAllNickNameUseCase: DeleteAllNickNameUseCase,
    private val deleteNickNameUseCase: DeleteNickNameUseCase,
    private val isExistsUseCase: IsExistsUseCase
) : ViewModel() {

    private val _bookmark = MutableLiveData<List<Bookmark>>()
    private val _isExists = MutableLiveData<Boolean>()

    val bookmark: LiveData<List<Bookmark>>
        get() = _bookmark
    val isExists: LiveData<Boolean>
        get() = _isExists

    fun getAllNickName() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataList = getAllNickNamesUseCase.execute()
            _bookmark.postValue(dataList)
        }
    }

    fun insertNickName(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            insertNickNamesUseCase.execute(bookmark)
        }
    }

    fun deleteNickName(bookmark: Bookmark) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNickNameUseCase.execute(bookmark)
        }
    }

    fun deleteAllNickName() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllNickNameUseCase.execute()
        }
    }

    fun isExists(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isExistsUseCase.execute(nickName)
        }
    }

}