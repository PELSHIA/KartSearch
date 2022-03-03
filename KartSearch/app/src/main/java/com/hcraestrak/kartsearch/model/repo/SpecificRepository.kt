package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class SpecificRepository(private val service: MatchService) {
    suspend fun specificMatchInquiry(matchId: String): Response<MatchDetailPlayer> {
        return service.specificMatchInquiry(matchId)
    }

    suspend fun specificTeamMatchInquiry(matchId: String): Response<MatchDetailTeam> {
        return service.specificTeamMatchInquiry(matchId)
    }
}