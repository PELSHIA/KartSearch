package com.hcraestrak.domain.useCase.local.bookmark

import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.repository.BookmarkRepository

class InsertNickNamesUseCase(private val repository: BookmarkRepository) {
    suspend fun execute(bookMark: Bookmark): Void {
        return repository.insertNickName(bookMark)
    }
}