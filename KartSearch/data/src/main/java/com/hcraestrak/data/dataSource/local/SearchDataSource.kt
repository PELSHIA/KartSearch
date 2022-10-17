package com.hcraestrak.data.dataSource.local

import com.hcraestrak.data.local.model.SearchEntity
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
    suspend fun getAll(): Flow<List<SearchEntity>>

    suspend fun insertWord(search: SearchEntity): Void

    suspend fun deleteWord(search: SearchEntity): Void

    suspend fun deleteAllWord(): Void
}