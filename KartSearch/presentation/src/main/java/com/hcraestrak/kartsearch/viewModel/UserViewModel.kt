package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.model.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {

    lateinit var job: Job

    private var _userInfoLiveData = MutableLiveData<UserInfo>()

    val userInfoLiveData: LiveData<UserInfo>
        get() = _userInfoLiveData

    fun getAccessId(nickName: String) {
        _userInfoLiveData = MutableLiveData<UserInfo>()
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getAccessId(nickName)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _userInfoLiveData.postValue(response.body())
                } else {
                    Log.d("Error", response.message())
                    _userInfoLiveData.postValue(null)
                }
            }
        }
    }

    fun getNickname(accessId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getNickname(accessId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _userInfoLiveData.postValue(response.body())
                } else {
                    Log.d("Error", response.message())
                    _userInfoLiveData.postValue(null)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}