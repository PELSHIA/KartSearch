package com.hcraestrak.domain.useCase.local.bookmark

import com.hcraestrak.domain.repository.BookmarkRepository

class IsExistsUseCase(private val repository: BookmarkRepository) {
    suspend fun execute(nickName: String): Boolean {
        return repository.isExists(nickName)
    }
}