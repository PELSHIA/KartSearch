package com.hcraestrak.data.dataSource.local

import com.hcraestrak.data.local.model.SearchEntity
import com.hcraestrak.data.local.service.SearchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchDataSourceImpl(private val searchService: SearchService): SearchDataSource {
    override suspend fun getAll(): Flow<List<SearchEntity>> = flow {
        emit(searchService.getAll())
    }

    override suspend fun insertWord(search: SearchEntity): Void {
        return searchService.insertWord(search)
    }

    override suspend fun deleteWord(search: SearchEntity): Void {
        return searchService.deleteWord(search)
    }

    override suspend fun deleteAllWord(): Void {
        return searchService.deleteAllWord()
    }

}