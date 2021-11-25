package com.hcraestrak.kartsearch.model.network

import com.google.gson.Gson
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val URL = "https://api.nexon.co.kr/"

    private val gson = Gson().newBuilder().setLenient().create()

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(TokenInterceptor()).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    val userService: UserService = retrofit.create(UserService::class.java)
    val matchService: MatchService = retrofit.create(MatchService::class.java)
}