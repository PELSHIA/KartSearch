package com.hcraestrak.data.repository

import com.hcraestrak.data.dataSource.local.SearchDataSource
import com.hcraestrak.data.mapper.mapperToSearchEntity
import com.hcraestrak.data.mapper.mapperToSearchList
import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.domain.repository.BookmarkRepository
import com.hcraestrak.domain.repository.SearchRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

class SearchRepositoryImpl(private val dataSource: SearchDataSource): SearchRepository {
    override suspend fun getAll(): List<Search> {
        val list = dataSource.getAll().flatMapConcat {
            it.asFlow()
        }.toList()
        return mapperToSearchList(list)
    }

    override suspend fun insertWord(search: Search): Void {
        return dataSource.insertWord(mapperToSearchEntity(search))
    }

    override suspend fun deleteWord(search: Search): Void {
        return dataSource.deleteWord(mapperToSearchEntity(search))
    }

    override suspend fun deleteAllWord(): Void {
        return dataSource.deleteAllWord()
    }

}