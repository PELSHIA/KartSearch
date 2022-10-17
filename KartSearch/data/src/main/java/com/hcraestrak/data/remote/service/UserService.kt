package com.hcraestrak.data.remote.service

import com.hcraestrak.data.remote.model.UserInfoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/kart/v1.0/users/{access_id}")
    suspend fun accessIdInquiry(@Path("access_id") access_Id: String): Response<UserInfoEntity>

    @GET("/kart/v1.0/users/nickname/{nickname}")
    suspend fun nickNameInquiry(@Path("nickname") nickName: String): Response<UserInfoEntity>
}