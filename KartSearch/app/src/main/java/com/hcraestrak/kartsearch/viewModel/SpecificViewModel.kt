package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificViewModel: ViewModel() {

    private val matchService: MatchService by lazy { RetrofitService.matchService }
    private val matchDetail = MutableLiveData<MatchDetailPlayer>()
    private val matchTeamDetail = MutableLiveData<MatchDetailTeam>()

    fun matchDetailObserver() = matchDetail
    fun matchTeamDetailObserver() = matchTeamDetail

    fun specificMatchInquiry(matchId: String) {
        matchService.specificMatchInquiry(matchId).enqueue(object: Callback<MatchDetailPlayer>{
            override fun onResponse(
                call: Call<MatchDetailPlayer>,
                response: Response<MatchDetailPlayer>
            ) {
                if (response.isSuccessful) {
                    matchDetail.postValue(response.body())
                } else {
                    matchDetail.postValue(null)
                }
            }

            override fun onFailure(call: Call<MatchDetailPlayer>, t: Throwable) {
                Log.d("Error", t.message.toString())
                matchDetail.postValue(null)
            }
        })
    }

    fun specificTeamMatchInquiry(matchId: String) {
        matchService.specificTeamMatchInquiry(matchId).enqueue(object: Callback<MatchDetailTeam> {
            override fun onResponse(
                call: Call<MatchDetailTeam>,
                response: Response<MatchDetailTeam>
            ) {
                if (response.isSuccessful) {
                    matchTeamDetail.postValue(response.body())
                } else {
                    matchTeamDetail.postValue(null)
                }
            }

            override fun onFailure(call: Call<MatchDetailTeam>, t: Throwable) {
                Log.d("Error", t.message.toString())
                matchTeamDetail.postValue(null)
            }
        })
    }
}