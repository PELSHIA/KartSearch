package com.hcraestrak.kartsearch.model.network

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTQwOTgzMTY5NiIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTM5MyIsIlgtQXBwLVJhdGUtTGltaXQiOiI1MDA6MTAiLCJuYmYiOjE2Mzg2ODI2NDMsImV4cCI6MTY1NDIzNDY0MywiaWF0IjoxNjM4NjgyNjQzfQ.3TemvuJHLKX-VnhIJSQ9DuD8zkmRpsYqa-FXyRTMmTQ"

    fun init(context: Context) {
        preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        token = ACCESS_TOKEN
    }

    var token : String?
        get() =
            preferences.getString("token", null)
        set(value) =
            preferences.edit().putString("token", value).apply()


}