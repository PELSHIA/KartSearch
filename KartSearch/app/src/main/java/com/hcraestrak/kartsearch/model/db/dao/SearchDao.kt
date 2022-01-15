package com.hcraestrak.kartsearch.model.db.dao

import androidx.room.*
import com.hcraestrak.kartsearch.model.db.entity.Search
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Completable

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_Table")
    fun getAll(): Flowable<List<Search>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(search: Search): Completable

    @Delete
    fun deleteWord(search: Search): Completable

    @Query("DELETE FROM search_Table")
    fun deleteAllWord(): Completable
}