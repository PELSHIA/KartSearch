package com.hcraestrak.kartsearch.model.network.data.response

data class Match(
    val matchType: String,
    val matches: List<MatchInfo>
)
