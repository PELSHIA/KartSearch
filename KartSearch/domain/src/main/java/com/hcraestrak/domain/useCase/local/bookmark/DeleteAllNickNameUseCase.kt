package com.hcraestrak.domain.useCase.local.bookmark

import com.hcraestrak.domain.repository.BookmarkRepository

class DeleteAllNickNameUseCase(private val repository: BookmarkRepository) {
    suspend fun execute(): Void {
        return repository.deleteAllNickNames()
    }
}