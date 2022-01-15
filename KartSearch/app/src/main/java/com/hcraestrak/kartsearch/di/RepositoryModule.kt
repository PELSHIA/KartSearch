package com.hcraestrak.kartsearch.di

import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.repo.MatchRepository
import com.hcraestrak.kartsearch.model.repo.SpecificRepository
import com.hcraestrak.kartsearch.model.repo.UserRepository
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
}