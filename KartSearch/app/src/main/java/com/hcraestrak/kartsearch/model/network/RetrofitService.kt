package com.hcraestrak.kartsearch.model.network

import android.util.Log
import com.google.gson.Gson
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object RetrofitService {
    private const val URL = "https://api.nexon.co.kr/"

    private val gson = Gson().newBuilder().setLenient().create()

    //    val logginInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    //        override fun log(message: String) {
    //            try {
    //                Log.d("TAG", "JSON      " + JSONObject(message).toString(4))
    //            } catch (e: Exception) {
    //                Log.d("TAG", "?      " + message)
    //            }
    //        }
    //
    //    }).setLevel(HttpLoggingInterceptor.Level.BODY)

//    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(TokenInterceptor()).addInterceptor(logginInterceptor).build()
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