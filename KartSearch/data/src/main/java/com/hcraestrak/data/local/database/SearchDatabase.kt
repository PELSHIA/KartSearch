package com.hcraestrak.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hcraestrak.data.local.model.SearchEntity
import com.hcraestrak.data.local.service.SearchService

@Database(entities = [SearchEntity::class], version = 1)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun SearchService(): SearchService

    companion object {
        private var instance: SearchDatabase? = null

//        @Synchronized
//        fun getInstance(context: Context): SearchDatabase? {
//            if (instance == null) {
//                synchronized(SearchDatabase::class){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        SearchDatabase::class.java,
//                        "search_Database"
//                    ).build()
//                }
//            }
//            return instance
//        }

        fun getInstance(context: Context): SearchDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SearchDatabase::class.java,
                    "search_Database"
                ).build()
            }
            return instance
        }
    }
}