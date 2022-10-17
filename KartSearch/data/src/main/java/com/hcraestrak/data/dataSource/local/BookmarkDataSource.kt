package com.hcraestrak.data.dataSource.local

import com.hcraestrak.data.local.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkDataSource {
    suspend fun getAllNickNames(): Flow<List<BookmarkEntity>>

    suspend fun insertNickName(bookmark: BookmarkEntity): Void

    suspend fun deleteNickName(bookmark: BookmarkEntity): Void

    suspend fun deleteAllNickNames(): Void

    suspend fun isExists(nickName: String): Boolean
}