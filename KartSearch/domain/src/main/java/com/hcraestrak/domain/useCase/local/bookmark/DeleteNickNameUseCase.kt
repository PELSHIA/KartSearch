package com.hcraestrak.domain.useCase.local.bookmark

import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.repository.BookmarkRepository

class DeleteNickNameUseCase(private val repository: BookmarkRepository) {
    suspend fun execute(bookmark: Bookmark): Void {
        return repository.deleteNickName(bookmark)
    }
}