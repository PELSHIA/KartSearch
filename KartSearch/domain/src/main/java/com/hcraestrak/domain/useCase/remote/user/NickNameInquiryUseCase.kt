package com.hcraestrak.domain.useCase.remote.user

import com.hcraestrak.domain.model.remote.UserInfo
import com.hcraestrak.domain.repository.UserRepository

class NickNameInquiryUseCase(private val repository: UserRepository) {
    suspend fun execute(access_id: String): UserInfo? {
        return repository.nickNameInquiry(access_id)
    }
}