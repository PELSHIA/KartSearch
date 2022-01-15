package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.db.entity.Search
import com.hcraestrak.kartsearch.model.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepository) : ViewModel() {
    private val searchWord = MutableLiveData<List<Search>>()

    fun searchWordObserver() = searchWord

    fun getAllWord() {
        repo.getAllWord()!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchWord.postValue(it)
            }, {
                Log.d("Error", it.message.toString())
                searchWord.postValue(null)
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