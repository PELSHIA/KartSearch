package com.hcraestrak.kartsearch.di

import com.hcraestrak.domain.repository.BookmarkRepository
import com.hcraestrak.domain.repository.MatchRepository
import com.hcraestrak.domain.repository.SearchRepository
import com.hcraestrak.domain.repository.UserRepository
import com.hcraestrak.domain.useCase.local.bookmark.*
import com.hcraestrak.domain.useCase.local.search.DeleteAllUseCase
import com.hcraestrak.domain.useCase.local.search.DeleteWordUseCase
import com.hcraestrak.domain.useCase.local.search.GetAllUseCase
import com.hcraestrak.domain.useCase.local.search.InsertWordUseCase
import com.hcraestrak.domain.useCase.remote.match.AccessIdMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.AllMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.SpecificSingleMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.match.SpecificTeamMatchInquiryUseCase
import com.hcraestrak.domain.useCase.remote.user.AccessIdInquiryUseCase
import com.hcraestrak.domain.useCase.remote.user.NickNameInquiryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /* === Local === */

    /* Search */

    @Singleton
    @Provides
    fun provideGetAllUseCase(repository: SearchRepository): GetAllUseCase{
        return GetAllUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideInsertWordUseCase(repository: SearchRepository): InsertWordUseCase{
        return InsertWordUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteWordUseCase(repository: SearchRepository): DeleteWordUseCase {
        return DeleteWordUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteAllUseCase(repository: SearchRepository): DeleteAllUseCase {
        return DeleteAllUseCase(repository)
    }

    /* bookmark */

    @Singleton
    @Provides
    fun provideGetAllNickNameUseCase(repository: BookmarkRepository): GetAllNickNamesUseCase {
        return GetAllNickNamesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideInsertNickNameUseCase(repository: BookmarkRepository): InsertNickNamesUseCase {
        return InsertNickNamesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteNickNameUseCase(repository: BookmarkRepository): DeleteNickNameUseCase {
        return DeleteNickNameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteAllNickNameUseCase(repository: BookmarkRepository): DeleteAllNickNameUseCase {
        return DeleteAllNickNameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideIsExistsUseCase(repository: BookmarkRepository): IsExistsUseCase {
        return IsExistsUseCase(repository)
    }

    /* === Remote === */

    /* User */

    @Singleton
    @Provides
    fun provideAccessIdInquiryUseCase(repository: UserRepository): AccessIdInquiryUseCase {
        return AccessIdInquiryUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideNickNameInquiryUseCase(repository: UserRepository): NickNameInquiryUseCase {
        return NickNameInquiryUseCase(repository)
    }

    /* Match */

    @Singleton
    @Provides
    fun provideAccessIdMatchInquiryUseCase(repository: MatchRepository): AccessIdMatchInquiryUseCase {
        return AccessIdMatchInquiryUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideAllMatchInquiryUseCase(repository: MatchRepository): AllMatchInquiryUseCase {
        return AllMatchInquiryUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSpecificSingleMatchInquiryUseCase(repository: MatchRepository): SpecificSingleMatchInquiryUseCase {
        return SpecificSingleMatchInquiryUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSpecificTeamMatchInquiryUseCase(repository: MatchRepository): SpecificTeamMatchInquiryUseCase {
        return SpecificTeamMatchInquiryUseCase(repository)
    }
}