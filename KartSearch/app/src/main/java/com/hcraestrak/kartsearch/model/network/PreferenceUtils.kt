package com.hcraestrak.kartsearch.model.network

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiMTQwOTgzMTY5NiIsImF1dGhfaWQiOiIyIiwidG9rZW5fdHlwZSI6IkFjY2Vzc1Rva2VuIiwic2VydmljZV9pZCI6IjQzMDAxMTM5MyIsIlgtQXBwLVJhdGUtTGltaXQiOiI1MDA6MTAiLCJuYmYiOjE2Mzc3NDAzNzUsImV4cCI6MTY1MzI5MjM3NSwiaWF0IjoxNjM3NzQwMzc1fQ.jDg_Ro2haGIyyIyZAN7qSOgWQlqwjxTzn2Ffu7OGCT8"

    fun init(context: Context) {
        preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        preferences.edit().putString("token", ACCESS_TOKEN).apply()
    }

    fun getToken() = preferences.getString("token", null)

}