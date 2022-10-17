package com.hcraestrak.domain.useCase.remote.match

import com.hcraestrak.domain.model.remote.Match
import com.hcraestrak.domain.repository.MatchRepository

class AllMatchInquiryUseCase(private val repository: MatchRepository) {
    suspend fun execute(start_date: String? = null,
                        end_date: String? = null,
                        offset: Int = 0,
                        limit: Int = 100,
                        match_types: String? = null): Match? {
        return repository.allMatchInquiry(start_date, end_date, offset, limit, match_types)
    }
}