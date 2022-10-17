package com.hcraestrak.domain.repository

import com.hcraestrak.domain.model.local.Search

interface SearchRepository {
    suspend fun getAll(): List<Search>

    suspend fun insertWord(search: Search): Void

    suspend fun deleteWord(search: Search): Void

    suspend fun deleteAllWord(): Void
}