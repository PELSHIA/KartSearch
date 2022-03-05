package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.db.dao.BookmarkDao
import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.model.db.entity.Search

class BookmarkRepository(private val db: BookmarkDao?) {

    suspend fun getAllNickName(): List<Bookmark>? {
        return db?.getAllNickNames()
    }

    suspend fun insertNickName(bookmark: Bookmark): Void? {
        return db?.insertNickName(bookmark)
    }

    suspend fun deleteNickName(bookmark: Bookmark): Void? {
        return db?.deleteNickName(bookmark)
    }

    suspend fun deleteAllNickName(): Void? {
        return db?.deleteAllNickNames()
    }
}