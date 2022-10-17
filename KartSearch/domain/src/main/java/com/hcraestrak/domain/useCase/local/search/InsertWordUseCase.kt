package com.hcraestrak.domain.useCase.local.search

import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.domain.repository.SearchRepository

class InsertWordUseCase(private val repository: SearchRepository) {
    suspend fun execute(search: Search): Void {
        return repository.insertWord(search)
    }
}