package com.hcraestrak.domain.useCase.local.search

import com.hcraestrak.domain.repository.SearchRepository

class DeleteAllUseCase(private val repository: SearchRepository) {
    suspend fun execute(): Void {
        return repository.deleteAllWord()
    }
}