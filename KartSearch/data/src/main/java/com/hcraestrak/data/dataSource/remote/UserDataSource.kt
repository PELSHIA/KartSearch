package com.hcraestrak.data.dataSource.remote

import com.hcraestrak.data.remote.model.UserInfoEntity
import retrofit2.Response

interface UserDataSource {
    suspend fun accessIdInquiry(access_Id: String): UserInfoEntity?

    suspend fun nickNameInquiry(nickName: String): UserInfoEntity?
}