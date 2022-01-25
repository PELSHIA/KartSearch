package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.model.repo.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val repo: MatchRepository) : ViewModel() {

    private var matchResponse = MutableLiveData<Match>()

    fun getMatchResponseObserver() : MutableLiveData<Match> {
        return matchResponse
    }

    fun accessIdMatchInquiry(access_Id: String, matchType: String, limit: Int = 10) {
        matchResponse = MutableLiveData()
        repo.accessIdMatchInquiry(access_Id, matchType, limit).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    matchResponse.postValue(response.body())
                } else {
                    matchResponse.postValue(null)
                }
            }, {
                Log.d("Error", it.message.toString())
                matchResponse.postValue(null)
            })
    }
}