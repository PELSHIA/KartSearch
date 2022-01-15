package com.hcraestrak.kartsearch.di

import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.MatchService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return RetrofitService.userService
    }

    @Singleton
    @Provides
    fun provideMatchService(): MatchService {
        return RetrofitService.matchService
    }

}