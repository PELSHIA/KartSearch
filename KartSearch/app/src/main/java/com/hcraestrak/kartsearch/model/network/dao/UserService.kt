package com.hcraestrak.kartsearch.model.network.dao

import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/kart/v1.0/users/{access_id}")
    fun accessIdInquiry(@Path("access_id") access_Id: String): Call<UserInfo>

    @GET("/kart/v1.0/users/nickname/{nickname}")
    fun nickNameInquiry(@Path("nickname") nickName: String): Call<UserInfo>
}