package com.hcraestrak.kartsearch.model.network.data.response

data class MatcheX(
    val accountNo: String,
    val channelName: String,
    val character: String,
    val endTime: String,
    val matchId: String,
    val matchResult: String,
    val matchType: String,
    val player: Player,
    val playerCount: Int,
    val seasonType: String,
    val startTime: String,
    val teamId: String,
    val trackId: String
)