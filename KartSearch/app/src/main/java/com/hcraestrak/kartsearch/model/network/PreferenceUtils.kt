package com.hcraestrak.kartsearch.model.network

import android.content.Context
import android.content.SharedPreferences
import com.hcraestrak.kartsearch.BuildConfig

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences
    private const val ACCESS_TOKEN = BuildConfig.API_KEY

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