package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.Match
import retrofit2.Response

class MatchRepository(private val service: MatchService) {
    suspend fun accessIdMatchInquiry(accessId: String, gameType: String, limit: Int = 100): Response<Match> {
        return service.accessIdMatchInquiry(accessId, match_types = gameType, limit = limit)
    }
}