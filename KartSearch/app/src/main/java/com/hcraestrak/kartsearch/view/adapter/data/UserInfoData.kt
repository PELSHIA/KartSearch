package com.hcraestrak.kartsearch.view.adapter.data

import android.graphics.Bitmap
import java.util.*

data class UserInfoData(
    val playerCount: Int,
    val userRank: Int,
    val kart: Bitmap,
    val track: String,
    val time: Date
)
