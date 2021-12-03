package com.hcraestrak.kartsearch.model.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.PreferenceUtils
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.MatchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchViewModel: ViewModel() {

    private val matchResponse = MutableLiveData<MatchResponse>()
    private val matchService: MatchService by lazy { RetrofitService.matchService }

   fun getMatchResponseObserver(): MutableLiveData<MatchResponse> {
       return matchResponse
   }

    fun accessIdMatchInquiry(access_Id: String) {
        matchService.accessIdMatchInquiry(access_Id).enqueue(object: Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                Log.d("accessIdMatchInquiry : ",  "${response.code()} ${response.message()}: ${response.body()}")
                if (response.isSuccessful) {
                    matchResponse.postValue(response.body())
                } else {
                    matchResponse.postValue(null)
                }
            }

            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                matchResponse.postValue(null)
            }
        })
    }
}