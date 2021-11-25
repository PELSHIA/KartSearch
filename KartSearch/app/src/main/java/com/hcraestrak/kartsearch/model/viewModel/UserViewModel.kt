package com.hcraestrak.kartsearch.model.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.PreferenceUtils
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTQwOTgzMTY5NiIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTM5MyIsIlgtQXBwLVJhdGUtTGltaXQiOiI1MDA6MTAiLCJuYmYiOjE2Mzc3NDAzNzUsImV4cCI6MTY1MzI5MjM3NSwiaWF0IjoxNjM3NzQwMzc1fQ.jDg_Ro2haGIyyIyZAN7qSOgWQlqwjxTzn2Ffu7OGCT8"

    private val userService: UserService by lazy { RetrofitService.userService }
    private val userInfoLiveData = MutableLiveData<UserInfo>()

    fun getObserver() : LiveData<UserInfo> {
        return userInfoLiveData
    }

    fun getAccessId(nickName: String) {
        PreferenceUtils.token = token
        userService.nickNameInquiry(nickName).enqueue(object: Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("getAccessId : ",  "${response.code()} ${response.message()}: ${response.body()}")
                if (response.isSuccessful) {
                    userInfoLiveData.postValue(response.body())
                } else {
                    userInfoLiveData.postValue(null )
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                userInfoLiveData.postValue(null)
            }
        })
    }
}