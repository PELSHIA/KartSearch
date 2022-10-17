package com.hcraestrak.domain.repository

import com.hcraestrak.domain.model.remote.UserInfo

interface UserRepository {
    suspend fun accessIdInquiry(access_id: String): UserInfo?

    suspend fun nickNameInquiry(nickName: String): UserInfo?
}