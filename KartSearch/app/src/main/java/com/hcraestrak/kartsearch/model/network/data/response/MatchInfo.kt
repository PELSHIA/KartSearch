package com.hcraestrak.kartsearch.model.network.data.response

import java.util.*

data class MatchInfo(
    val accountNo: String,
    val matchId: String,
    val matchType: String,
    val teamID: String,
    val character: String,
    val startTime: Date,
    val endTime: Date,
    val playTime: Int,
    val channelName: String,
    val trackId: String,
    val playerCount: Int,
    val player: PlayerInfo
)
