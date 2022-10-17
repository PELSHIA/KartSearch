package com.hcraestrak.data.local.service

import androidx.room.*
import com.hcraestrak.data.local.model.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchService {
    @Query("SELECT * FROM search_Table")
    suspend fun getAll(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(search: SearchEntity): Void

    @Delete
    suspend fun deleteWord(search: SearchEntity): Void

    @Query("DELETE FROM search_Table")
    suspend fun deleteAllWord(): Void
}