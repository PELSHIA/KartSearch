package com.hcraestrak.kartsearch.model.network

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    }

    var token: String?
        get() =
            preferences.getString("token", null)
        set(value) =
            preferences.edit().putString("token", value).apply()
}