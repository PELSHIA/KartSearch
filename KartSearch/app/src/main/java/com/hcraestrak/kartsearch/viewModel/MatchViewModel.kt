package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.model.repo.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val repo: MatchRepository) : ViewModel() {

    private var _matchResponse = MutableLiveData<Match>()
    val matchResponse: LiveData<Match>
        get() = _matchResponse

    fun accessIdMatchInquiry(access_Id: String, matchType: String, limit: Int = 10) {
        _matchResponse = MutableLiveData()
        repo.accessIdMatchInquiry(access_Id, matchType, limit).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    _matchResponse.postValue(response.body())
                } else {
                    _matchResponse.postValue(null)
                }
            }, {
                Log.d("Error", it.message.toString())
                _matchResponse.postValue(null)
            })
    }
}