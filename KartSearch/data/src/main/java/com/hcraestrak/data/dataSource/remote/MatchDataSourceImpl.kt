package com.hcraestrak.data.dataSource.remote

import android.util.Log
import com.hcraestrak.data.remote.model.DetailSingleEntity
import com.hcraestrak.data.remote.model.DetailTeamEntity
import com.hcraestrak.data.remote.model.MatchEntity
import com.hcraestrak.data.remote.service.MatchService

class MatchDataSourceImpl(private val matchService: MatchService): MatchDataSource {
    override suspend fun accessIdMatchInquiry(
        access_id: String,
        start_date: String?,
        end_date: String?,
        offset: Int,
        limit: Int,
        match_type: String?
    ): MatchEntity? {
        val response = matchService.accessIdMatchInquiry(access_id, start_date, end_date, offset, limit, match_type)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }

    override suspend fun allMatchInquiry(
        start_date: String?,
        end_date: String?,
        offset: Int,
        limit: Int,
        match_type: String?
    ): MatchEntity? {
        val response = matchService.allMatchInquiry(start_date, end_date, offset, limit, match_type)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }

    override suspend fun specificSingleMatchInquiry(match_id: String): DetailSingleEntity? {
        val response = matchService.specificSingleMatchInquiry(match_id)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }

    override suspend fun specificTeamMatchInquiry(match_id: String): DetailTeamEntity? {
        val response = matchService.specificTeamMatchInquiry(match_id)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }


}