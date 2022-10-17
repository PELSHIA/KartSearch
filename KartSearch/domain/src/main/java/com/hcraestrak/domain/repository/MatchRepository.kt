package com.hcraestrak.domain.repository

import com.hcraestrak.domain.model.remote.DetailSingle
import com.hcraestrak.domain.model.remote.DetailTeam
import com.hcraestrak.domain.model.remote.Match

interface MatchRepository {
    suspend fun accessIdMatchInquiry(access_id: String, start_date: String? = null, end_date: String? = null, offset: Int = 0, limit: Int = 100, match_type: String? = null): Match

    suspend fun allMatchInquiry(start_date: String? = null, end_date: String? = null, offset: Int = 0, limit: Int = 100, match_type: String? = null): Match

    suspend fun specificSingleMatchInquiry(match_id: String): DetailSingle

    suspend fun specificTeamMatchInquiry(match_id: String): DetailTeam
}