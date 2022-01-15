package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import com.hcraestrak.kartsearch.model.repo.SpecificRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SpecificViewModel @Inject constructor(private val repo: SpecificRepository): ViewModel() {

    private val matchDetail = MutableLiveData<MatchDetailPlayer>()
    private val matchTeamDetail = MutableLiveData<MatchDetailTeam>()

    fun matchDetailObserver() = matchDetail
    fun matchTeamDetailObserver() = matchTeamDetail

    fun specificMatchInquiry(matchId: String) {
        repo.specificMatchInquiry(matchId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    matchDetail.postValue(response.body())
                } else {
                    matchDetail.postValue(null)
                }
            }, {
                Log.d("Error", it.message.toString())
                matchDetail.postValue(null)
            })
    }

    fun specificTeamMatchInquiry(matchId: String) {
        repo.specificTeamMatchInquiry(matchId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    matchTeamDetail.postValue(response.body())
                } else {
                    matchTeamDetail.postValue(null)
                }
            }, {
                Log.d("Error", it.message.toString())
                matchTeamDetail.postValue(null)
            })
    }
}