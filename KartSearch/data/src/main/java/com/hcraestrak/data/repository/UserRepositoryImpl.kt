package com.hcraestrak.data.repository

import com.hcraestrak.data.dataSource.remote.UserDataSource
import com.hcraestrak.data.mapper.mapperToUserInfo
import com.hcraestrak.domain.model.remote.UserInfo
import com.hcraestrak.domain.repository.UserRepository

class UserRepositoryImpl(private val dataSource: UserDataSource): UserRepository {
    override suspend fun accessIdInquiry(access_id: String): UserInfo {
        return mapperToUserInfo(dataSource.accessIdInquiry(access_id)!!)!!
    }

    override suspend fun nickNameInquiry(nickName: String): UserInfo {
        return mapperToUserInfo(dataSource.nickNameInquiry(nickName)!!)!!
    }
}