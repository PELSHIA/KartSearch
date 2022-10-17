package com.hcraestrak.kartsearch.di

import com.hcraestrak.data.dataSource.local.BookmarkDataSource
import com.hcraestrak.data.dataSource.local.BookmarkDataSourceImpl
import com.hcraestrak.data.dataSource.local.SearchDataSource
import com.hcraestrak.data.dataSource.local.SearchDataSourceImpl
import com.hcraestrak.data.dataSource.remote.MatchDataSource
import com.hcraestrak.data.dataSource.remote.MatchDataSourceImpl
import com.hcraestrak.data.dataSource.remote.UserDataSource
import com.hcraestrak.data.dataSource.remote.UserDataSourceImpl
import com.hcraestrak.data.local.service.BookmarkService
import com.hcraestrak.data.local.service.SearchService
import com.hcraestrak.data.remote.service.MatchService
import com.hcraestrak.data.remote.service.UserService
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
object DataSourceModule {
    @Singleton
    @Provides
    fun provideUserRepository(service: UserService): UserDataSource {
        return UserDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideMatchRepository(service: MatchService): MatchDataSource {
        return MatchDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideBookmarkRepository(service: BookmarkService): BookmarkDataSource {
        return BookmarkDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(service: SearchService): SearchDataSource {
        return SearchDataSourceImpl(service)
    }
}