package com.hcraestrak.kartsearch.model.network.data.response

import java.util.*

data class MatchDetailPlayer(
    val matchId: String,
    val matchType: String,
    val matchResult: String,
    val gameSpeed: Int,
    val startTime: String,
    val endTime: String,
    val playTime: Int,
    val channelName: String,
    val trackId: String,
    val players: List<Player>
)
