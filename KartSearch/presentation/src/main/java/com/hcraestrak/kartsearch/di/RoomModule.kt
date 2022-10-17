package com.hcraestrak.kartsearch.di

import android.content.Context
import com.hcraestrak.data.local.database.BookmarkDatabase
import com.hcraestrak.data.local.database.SearchDatabase
import com.hcraestrak.data.local.service.BookmarkService
import com.hcraestrak.data.local.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideSearchDatabase(@ApplicationContext context: Context): SearchService {
        return SearchDatabase.getInstance(context)!!.SearchService()
    }

    @Singleton
    @Provides
    fun provideBookmarkDatabase(@ApplicationContext context: Context): BookmarkService {
        return BookmarkDatabase.getInstance(context)!!.BookmarkService()
    }
}