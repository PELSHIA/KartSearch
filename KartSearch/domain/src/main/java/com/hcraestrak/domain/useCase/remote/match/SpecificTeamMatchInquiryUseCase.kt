package com.hcraestrak.domain.useCase.remote.match

import com.hcraestrak.domain.model.remote.DetailTeam
import com.hcraestrak.domain.repository.MatchRepository

class SpecificTeamMatchInquiryUseCase(private val repository: MatchRepository) {
    suspend fun execute(match_id: String): DetailTeam? {
        return repository.specificTeamMatchInquiry(match_id)
    }
}