package com.hcraestrak.data.repository

import com.hcraestrak.data.dataSource.local.BookmarkDataSource
import com.hcraestrak.data.mapper.mapperToBookmarkEntity
import com.hcraestrak.data.mapper.mapperToBookmarkList
import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

class BookMarkRepositoryImpl(private val dataSource: BookmarkDataSource): BookmarkRepository {
    override suspend fun getAllNickNames(): List<Bookmark> {
        val list = dataSource.getAllNickNames().flatMapConcat {
            it.asFlow()
        }.toList()
        return mapperToBookmarkList(list)
    }

    override suspend fun insertNickName(bookmark: Bookmark): Void {
        return dataSource.insertNickName(mapperToBookmarkEntity(bookmark))
    }

    override suspend fun deleteNickName(bookmark: Bookmark): Void {
        return dataSource.deleteNickName(mapperToBookmarkEntity(bookmark))
    }

    override suspend fun deleteAllNickNames(): Void {
        return dataSource.deleteAllNickNames()
    }

    override suspend fun isExists(nickName: String): Boolean {
        return dataSource.isExists(nickName)
    }
}