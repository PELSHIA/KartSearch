package com.hcraestrak.kartsearch.di

import android.content.Context
import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.db.database.SearchDatabase
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
    fun provideSearchDatabase(@ApplicationContext context: Context): SearchDao? {
        return SearchDatabase.getInstance(context)?.SearchDao()
    }
}