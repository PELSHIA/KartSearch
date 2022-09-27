package com.hcraestrak.kartsearch.model.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.entity.Search

@Database(entities = [Search::class], version = 1)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun SearchDao(): SearchDao

    companion object {
        private var instance: SearchDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SearchDatabase? {
            if (instance == null) {
                synchronized(SearchDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchDatabase::class.java,
                        "search_Database"
                    ).build()
                }
            }
            return instance
        }
    }
}