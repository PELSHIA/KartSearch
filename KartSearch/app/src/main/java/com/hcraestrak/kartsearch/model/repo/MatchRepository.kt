package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.data.response.Match
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class MatchRepository(private val service: MatchService) {
    fun accessIdMatchInquiry(accessId: String, gameType: String): Single<Response<Match>> {
        return service.accessIdMatchInquiry(accessId, match_types = gameType)
    }
}