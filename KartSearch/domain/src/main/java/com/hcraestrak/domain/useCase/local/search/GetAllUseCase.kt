package com.hcraestrak.domain.useCase.local.search

import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.domain.repository.SearchRepository

class GetAllUseCase(private val repository: SearchRepository) {
    suspend fun execute(): List<Search> {
        return repository.getAll()
    }
}