package com.hcraestrak.kartsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hcraestrak.kartsearch.model.network.RetrofitService
import com.hcraestrak.kartsearch.model.network.dao.UserService
import com.hcraestrak.kartsearch.model.network.data.response.UserInfo
import com.hcraestrak.kartsearch.model.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {

    private val userInfoLiveData = MutableLiveData<UserInfo>()

    fun getObserver() : LiveData<UserInfo> {
        return userInfoLiveData
    }

    fun getAccessId(nickName: String) {
        repo.getAccessId(nickName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    userInfoLiveData.postValue(response.body())
                } else {
                    userInfoLiveData.postValue(null)
                }
            }, {
                Log.d("Error", "${it.message}}")
            })
    }
}