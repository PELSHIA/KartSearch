package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.entity.Search

class SearchRepository(private val db: SearchDao?) {

    suspend fun getAllWord(): List<Search>? {
        return db?.getAll()
    }

    suspend fun insertWord(search: Search): Void? {
        return db?.insertWord(search)
    }

    suspend fun deleteWord(search: Search): Void? {
        return db?.deleteWord(search)
    }

    suspend fun deleteAllWord(): Void? {
        return db?.deleteAllWord()
    }
}