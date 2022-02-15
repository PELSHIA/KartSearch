package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepository) : ViewModel() {
    private val _searchWord = MutableLiveData<List<Search>>()

    val searchWord: LiveData<List<Search>>
        get() = _searchWord

    fun searchWordObserver() = _searchWord

    fun getAllWord() {
        repo.getAllWord()!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchWord.postValue(it)
            }, {
                Log.d("Error", it.message.toString())
                _searchWord.postValue(null)
            })
    }

    fun insertWord(search: Search) {
        repo.insertWord(search)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteWord(search: Search) {
        repo.deleteWord(search)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteAllWord() {
        repo.deleteAllWord()!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}