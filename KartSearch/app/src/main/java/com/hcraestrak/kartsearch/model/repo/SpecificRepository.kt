package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class SpecificRepository(private val service: MatchService) {
    fun specificMatchInquiry(matchId: String): Single<Response<MatchDetailPlayer>> {
        return service.specificMatchInquiry(matchId)
    }

    fun specificTeamMatchInquiry(matchId: String): Single<Response<MatchDetailTeam>> {
        return service.specificTeamMatchInquiry(matchId)
    }
}