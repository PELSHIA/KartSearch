package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.entity.Search
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class SearchRepository(private val db: SearchDao?) {

    fun getAllWord(): Flowable<List<Search>>? {
        return db?.getAll()
    }

    fun insertWord(search: Search): Completable? {
        return db?.insertWord(search)
    }

    fun deleteWord(search: Search): Completable? {
        return db?.deleteWord(search)
    }

    fun deleteAllWord(): Completable? {
        return db?.deleteAllWord()
    }
}