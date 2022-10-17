package com.hcraestrak.data.local.service

import androidx.room.*
import com.hcraestrak.data.local.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.Nullable

@Dao
interface BookmarkService {
    @Query("SELECT * FROM bookmark_Table")
    suspend fun getAllNickNames(): List<BookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNickName(bookmark: BookmarkEntity): Void

    @Delete
    suspend fun deleteNickName(bookmark: BookmarkEntity): Void

    @Query("DELETE FROM bookmark_Table")
    suspend fun deleteAllNickNames(): Void

    @Query("SELECT EXISTS (SELECT * FROM bookmark_Table WHERE nickName = :nickName)")
    suspend fun isExists(nickName: String): Boolean
}