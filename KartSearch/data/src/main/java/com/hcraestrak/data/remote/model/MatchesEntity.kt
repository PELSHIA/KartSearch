package com.hcraestrak.data.remote.model

data class MatchesEntity(
    val matchType: String,
    val matches: List<MatchInfoEntity>
)