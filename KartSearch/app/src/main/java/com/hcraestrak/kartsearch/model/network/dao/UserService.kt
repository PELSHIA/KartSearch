package com.hcraestrak.kartsearch.model.network.dao

import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/kart/v1.0/users/{access_id}")
    suspend fun accessIdInquiry(@Path("access_id") access_Id: String): Response<UserInfo>

    @GET("/kart/v1.0/users/nickname/{nickname}")
    suspend fun nickNameInquiry(@Path("nickname") nickName: String): Response<UserInfo>
}