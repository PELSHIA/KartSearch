package com.hcraestrak.kartsearch.model.network.dao

import com.hcraestrak.kartsearch.model.network.data.response.Match
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailPlayer
import com.hcraestrak.kartsearch.model.network.data.response.MatchDetailTeam
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchService {
    @GET("/kart/v1.0/users/{access_id}/matches")
    suspend fun accessIdMatchInquiry(
        @Path("access_id") access_id: String,
        @Query("start_date") start_date: String? = null,
        @Query("end_date") end_date: String? = null,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("match_types") match_types: String
    ): Response<Match>

    @GET("/kart/v1.0/matches")
    suspend fun allMatchInquiry(
        @Query("start_date") start_date: String? = null,
        @Query("end_date") end_date: String? = null,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("match_types") match_types: String? = null
    ): Response<Match>

    @GET("/kart/v1.0/matches/{match_id}")
    suspend fun specificMatchInquiry(@Path("match_id") match_id: String): Response<MatchDetailPlayer>

    @GET("/kart/v1.0/matches/{match_id}")
    suspend fun specificTeamMatchInquiry(@Path("match_id") match_id: String): Response<MatchDetailTeam>

}