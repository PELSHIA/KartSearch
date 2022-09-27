package com.hcraestrak.kartsearch.di

import com.hcraestrak.kartsearch.model.db.dao.BookmarkDao
import com.hcraestrak.kartsearch.model.db.dao.SearchDao
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.repo.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(service: UserService): UserRepository {
        return UserRepository(service)
    }

    @Singleton
    @Provides
    fun provideMatchRepository(service: MatchService): MatchRepository {
        return MatchRepository(service)
    }

    @Singleton
    @Provides
    fun provideSpecificRepository(service: MatchService): SpecificRepository {
        return SpecificRepository(service)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(db: SearchDao?): SearchRepository {
        return SearchRepository(db)
    }

    @Singleton
    @Provides
    fun provideBookmarkRepository(db: BookmarkDao?): BookmarkRepository {
        return BookmarkRepository(db)
    }
}