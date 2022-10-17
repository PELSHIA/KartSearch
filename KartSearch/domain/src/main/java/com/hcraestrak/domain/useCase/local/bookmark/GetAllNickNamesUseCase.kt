package com.hcraestrak.domain.useCase.local.bookmark

import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.repository.BookmarkRepository

class GetAllNickNamesUseCase(private val repository: BookmarkRepository) {
    suspend fun execute(): List<Bookmark> {
        return repository.getAllNickNames()
    }
}