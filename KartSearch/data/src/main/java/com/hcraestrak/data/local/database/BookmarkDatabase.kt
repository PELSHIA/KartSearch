package com.hcraestrak.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hcraestrak.data.local.model.BookmarkEntity
import com.hcraestrak.data.local.service.BookmarkService

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun BookmarkService(): BookmarkService

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