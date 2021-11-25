package com.hcraestrak.kartsearch.app

import android.app.Application
import com.hcraestrak.kartsearch.model.network.PreferenceUtils

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.init(applicationContext)
    }
}