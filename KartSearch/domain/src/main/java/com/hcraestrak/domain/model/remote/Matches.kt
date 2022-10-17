package com.hcraestrak.domain.model.remote

data class Matches(
    val matchType: String,
    val matches: List<MatchInfo>
)