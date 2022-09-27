package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import retrofit2.Response

class UserRepository(private val service: UserService) {

    suspend fun getAccessId(nickName: String): Response<UserInfo> {
        return service.nickNameInquiry(nickName)
    }

    suspend fun getNickname(accessId: String): Response<UserInfo> {
        return service.accessIdInquiry(accessId)
    }

}