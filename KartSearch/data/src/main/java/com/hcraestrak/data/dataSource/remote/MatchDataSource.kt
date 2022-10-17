package com.hcraestrak.data.dataSource.remote

import com.hcraestrak.data.remote.model.DetailSingleEntity
import com.hcraestrak.data.remote.model.DetailTeamEntity
import com.hcraestrak.data.remote.model.MatchEntity
import retrofit2.Response

interface MatchDataSource {
    suspend fun accessIdMatchInquiry(access_id: String, start_date: String? = null, end_date: String? = null, offset: Int = 0, limit: Int = 100, match_type: String? = null): MatchEntity?

    suspend fun allMatchInquiry(start_date: String? = null, end_date: String? = null, offset: Int = 0, limit: Int = 100, match_type: String? = null): MatchEntity?

    suspend fun specificSingleMatchInquiry(match_id: String): DetailSingleEntity?

    suspend fun specificTeamMatchInquiry(match_id: String): DetailTeamEntity?
}