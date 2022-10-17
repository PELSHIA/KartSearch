package com.hcraestrak.data.dataSource.remote

import android.util.Log
import com.hcraestrak.data.remote.model.UserInfoEntity
import com.hcraestrak.data.remote.service.UserService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserDataSourceImpl(private val userService: UserService): UserDataSource {
    override suspend fun accessIdInquiry(access_Id: String): UserInfoEntity? {
        val response = userService.accessIdInquiry(access_Id)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }

    override suspend fun nickNameInquiry(nickName: String): UserInfoEntity? {
        val response = userService.nickNameInquiry(nickName)
        return if (response.isSuccessful && response.body() != null) {
            response.body()
        } else {
            Log.d("response", response.message())
            null
        }
    }

}