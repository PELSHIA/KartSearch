package com.hcraestrak.domain.repository

import com.hcraestrak.domain.model.local.Bookmark

interface BookmarkRepository {
    suspend fun getAllNickNames(): List<Bookmark>

    suspend fun insertNickName(bookmark: Bookmark): Void

    suspend fun deleteNickName(bookmark: Bookmark): Void

    suspend fun deleteAllNickNames(): Void

    suspend fun isExists(nickName: String): Boolean
}