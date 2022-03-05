package com.hcraestrak.kartsearch.model.db.dao

import androidx.room.*
import com.hcraestrak.kartsearch.model.db.entity.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_Table")
    suspend fun getAllNickNames(): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNickName(bookmark: Bookmark): Void

    @Delete
    suspend fun deleteNickName(bookmark: Bookmark): Void

    @Query("DELETE FROM bookmark_Table")
    suspend fun deleteAllNickNames(): Void
}