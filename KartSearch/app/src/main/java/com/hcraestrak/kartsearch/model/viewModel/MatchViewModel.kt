package com.hcraestrak.kartsearch.model.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.PreferenceUtils
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.Match
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchViewModel: ViewModel() {

    private var matchResponse = MutableLiveData<Match>()
    private val matchService: MatchService by lazy { RetrofitService.matchService }

    fun getMatchResponseObserver() : MutableLiveData<Match> {
        return matchResponse
    }

    fun accessIdMatchInquiryWithMatchType(access_Id: String, matchType: String) {
        matchResponse = MutableLiveData<Match>()
        matchService.accessIdMatchInquiry(access_id = access_Id, match_types = matchType).enqueue(object: Callback<Match> {
            override fun onResponse(call: Call<Match>, response: Response<Match>) {
                Log.d("accessIdMatchInquiry : ",  "${response.code()} ${response.message()}: ${response.body()}")
                if (response.isSuccessful) {
                    matchResponse.postValue(response.body())
                } else {
                    matchResponse.postValue(null)
                }
            }

            override fun onFailure(call: Call<Match>, t: Throwable) {
                matchResponse.postValue(null)
            }
        })
    }
}