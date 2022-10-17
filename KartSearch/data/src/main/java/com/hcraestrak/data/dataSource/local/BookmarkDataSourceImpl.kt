package com.hcraestrak.data.dataSource.local

import com.hcraestrak.data.local.model.BookmarkEntity
import com.hcraestrak.data.local.service.BookmarkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookmarkDataSourceImpl(private val bookmarkService: BookmarkService): BookmarkDataSource {
    override suspend fun getAllNickNames(): Flow<List<BookmarkEntity>> = flow {
        emit(bookmarkService.getAllNickNames())
    }

    override suspend fun insertNickName(bookmark: BookmarkEntity): Void {
        return bookmarkService.insertNickName(bookmark)
    }

    override suspend fun deleteNickName(bookmark: BookmarkEntity): Void {
        return bookmarkService.deleteNickName(bookmark)
    }

    override suspend fun deleteAllNickNames(): Void {
        return bookmarkService.deleteAllNickNames()
    }

    override suspend fun isExists(nickName: String): Boolean {
        return bookmarkService.isExists(nickName)
    }

}