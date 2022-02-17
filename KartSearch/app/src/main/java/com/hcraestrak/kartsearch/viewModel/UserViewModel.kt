package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.model.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {

    private val _userInfoLiveData = MutableLiveData<UserInfo>()

    val userInfoLiveData: LiveData<UserInfo>
        get() = _userInfoLiveData

    fun getAccessId(nickName: String) {
        repo.getAccessId(nickName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    _userInfoLiveData.postValue(response.body())
                } else {
                    _userInfoLiveData.postValue(null)
                }
            }, {
                Log.d("Error", "${it.message}}")
            })
    }
}