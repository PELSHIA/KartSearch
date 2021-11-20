package com.hcraestrak.kartsearch.model.network.data.response

import java.util.*

data class MatchDetailTeam(
    val matchId: String,
    val matchType: String,
    val matchResult: String,
    val gameSpeed: Int,
    val startTime: Date,
    val endTime: Date,
    val playTime: Int,
    val channelName: String,
    val trackId: String,
    val teams: List<Team>
)
