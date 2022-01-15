package com.hcraestrak.kartsearch.model.repo

import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class UserRepository(private val service: UserService) {

    fun getAccessId(nickName: String): Single<Response<UserInfo>> {
        return service.nickNameInquiry(nickName)
    }

}