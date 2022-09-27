package com.hcraestrak.kartsearch.model.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hcraestrak.kartsearch.model.db.dao.BookmarkDao
import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.entity.Bookmark
import com.hcraestrak.kartsearch.model.db.entity.Search

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun BookmarkDao(): BookmarkDao

    companion object {
        private var instance: BookmarkDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BookmarkDatabase? {
            if (instance == null) {
                synchronized(BookmarkDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkDatabase::class.java,
                        "bookmark_table"
                    ).build()
                }
            }
            return instance
        }
    }
}