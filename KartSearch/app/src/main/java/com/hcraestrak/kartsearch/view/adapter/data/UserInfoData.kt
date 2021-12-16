package com.hcraestrak.kartsearch.view.adapter.data

import android.graphics.Bitmap
import java.util.*

data class UserInfoData(
    val playerCount: Int,
    val userRank: String,
    val kart: String,
    val track: String,
    val time: String,
    val isWin: String,
    var isRetired: String
)
