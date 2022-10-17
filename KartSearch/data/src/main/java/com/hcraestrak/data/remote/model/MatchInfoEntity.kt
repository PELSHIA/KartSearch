package com.hcraestrak.data.remote.model

data class MatchInfoEntity(
    val accountNo: String,
    val channelName: String,
    val character: String,
    val endTime: String,
    val matchId: String,
    val matchResult: String,
    val matchType: String,
    val player: PlayerEntity,
    val playerCount: Int,
    val seasonType: String,
    val startTime: String,
    val teamId: String,
    val trackId: String
)