package com.hcraestrak.data.repository

import com.hcraestrak.data.dataSource.remote.MatchDataSource
import com.hcraestrak.data.mapper.mapperToDetailSingle
import com.hcraestrak.data.mapper.mapperToDetailTeam
import com.hcraestrak.data.mapper.mapperToMatch
import com.hcraestrak.domain.model.remote.DetailSingle
import com.hcraestrak.domain.model.remote.DetailTeam
import com.hcraestrak.domain.model.remote.Match
import com.hcraestrak.domain.repository.MatchRepository

class MatchRepositoryImpl(private val dataSource: MatchDataSource): MatchRepository {
    override suspend fun accessIdMatchInquiry(
        access_id: String,
        start_date: String?,
        end_date: String?,
        offset: Int,
        limit: Int,
        match_type: String?
    ): Match {
        val entity = dataSource.accessIdMatchInquiry(access_id, start_date, end_date, offset, limit, match_type)
        return mapperToMatch(entity!!)
    }

    override suspend fun allMatchInquiry(
        start_date: String?,
        end_date: String?,
        offset: Int,
        limit: Int,
        match_type: String?
    ): Match {
        val entity = dataSource.allMatchInquiry(start_date, end_date, offset, limit, match_type)
        return mapperToMatch(entity!!)
    }

    override suspend fun specificSingleMatchInquiry(match_id: String): DetailSingle {
        val entity = dataSource.specificSingleMatchInquiry(match_id)
        return mapperToDetailSingle(entity!!)
    }

    override suspend fun specificTeamMatchInquiry(match_id: String): DetailTeam {
        val entity = dataSource.specificTeamMatchInquiry(match_id)
        return mapperToDetailTeam(entity!!)
    }

}