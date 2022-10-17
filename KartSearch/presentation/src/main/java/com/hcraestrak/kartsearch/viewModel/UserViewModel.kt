package com.hcraestrak.kartsearch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcraestrak.domain.model.remote.UserInfo
import com.hcraestrak.domain.useCase.remote.user.AccessIdInquiryUseCase
import com.hcraestrak.domain.useCase.remote.user.NickNameInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val accessIdInquiryUseCase: AccessIdInquiryUseCase,
    private val nickNameInquiryUseCase: NickNameInquiryUseCase
): ViewModel() {

    private var _userInfoLiveData = MutableLiveData<UserInfo>()

    val userInfoLiveData: LiveData<UserInfo>
        get() = _userInfoLiveData

    fun accessIdInquiry(accessId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoData = accessIdInquiryUseCase.execute(accessId)
            _userInfoLiveData.postValue(userInfoData)
        }
    }

    fun nickNameInquiry(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoData = nickNameInquiryUseCase.execute(nickName)
            _userInfoLiveData.postValue(userInfoData)
        }
    }

}