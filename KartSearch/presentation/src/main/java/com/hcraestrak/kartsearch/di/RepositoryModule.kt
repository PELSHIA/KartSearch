package com.hcraestrak.kartsearch.di

import com.hcraestrak.data.dataSource.local.BookmarkDataSource
import com.hcraestrak.data.dataSource.local.SearchDataSource
import com.hcraestrak.data.dataSource.remote.MatchDataSource
import com.hcraestrak.data.dataSource.remote.UserDataSource
import com.hcraestrak.data.repository.BookMarkRepositoryImpl
import com.hcraestrak.data.repository.MatchRepositoryImpl
import com.hcraestrak.data.repository.SearchRepositoryImpl
import com.hcraestrak.data.repository.UserRepositoryImpl
import com.hcraestrak.domain.repository.BookmarkRepository
import com.hcraestrak.domain.repository.MatchRepository
import com.hcraestrak.domain.repository.SearchRepository
import com.hcraestrak.domain.repository.UserRepository
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
    fun provideUserRepository(source: UserDataSource): UserRepository {
        return UserRepositoryImpl(source)
    }

    @Singleton
    @Provides
    fun provideMatchRepository(source: MatchDataSource): MatchRepository {
        return MatchRepositoryImpl(source)
    }

    @Singleton
    @Provides
    fun provideBookmarkRepository(source: BookmarkDataSource): BookmarkRepository {
        return BookMarkRepositoryImpl(source)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(source: SearchDataSource): SearchRepository {
        return SearchRepositoryImpl(source)
    }

}