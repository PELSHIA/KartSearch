package com.hcraestrak.kartsearch.model.db.dao

import androidx.room.*
import com.hcraestrak.kartsearch.model.db.entity.Search

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_Table")
    suspend fun getAll(): List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(search: Search): Void

    @Delete
    suspend fun deleteWord(search: Search): Void

    @Query("DELETE FROM search_Table")
    suspend fun deleteAllWord(): Void
}