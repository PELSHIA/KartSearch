package com.hcraestrak.domain.useCase.remote.match

import com.hcraestrak.domain.model.remote.DetailSingle
import com.hcraestrak.domain.repository.MatchRepository

class SpecificSingleMatchInquiryUseCase(private val repository: MatchRepository) {
    suspend fun execute(match_id: String): DetailSingle? {
        return repository.specificSingleMatchInquiry(match_id)
    }
}