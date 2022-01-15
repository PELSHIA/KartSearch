package com.hcraestrak.kartsearch.app

import android.app.Application
import com.hcraestrak.kartsearch.model.network.PreferenceUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.init(applicationContext)
    }
}