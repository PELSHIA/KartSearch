package com.hcraestrak.domain.useCase.remote.user

import com.hcraestrak.domain.model.remote.UserInfo
import com.hcraestrak.domain.repository.UserRepository

class AccessIdInquiryUseCase(private val repository: UserRepository) {
    suspend fun execute(access_id: String): UserInfo? {
        return repository.accessIdInquiry(access_id)
    }
}