package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserInfoBinding
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.UserInfoViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class UserInfoFragment(val id: String) : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>(R.layout.fragment_user_info){

    override val viewModel: UserInfoViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNickName()
    }

    private fun getNickName() {
        userViewModel.getNickname(id)
        userViewModel.userInfoLiveData.observe(viewLifecycleOwner, {
            setProfileData(it.name, it.level)
            setRecordData(it.name)
        })
    }

    private fun setProfileData(nickName: String, level: Int) {
        viewModel.getProfileData(nickName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ infoData ->
                Log.d("infoData", infoData.toString())
                Glide.with(binding.userProfile.context).load(infoData.profileImg).into(binding.userProfile)
                Glide.with(binding.levelImg.context).load(infoData.levelImg).into(binding.levelImg)
                binding.levelTxt.text = "Lv.$level"
                binding.clubTxt.text = if (infoData.club != "가입된클럽이없습니다") infoData.club else "없음"
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    private fun setRecordData(nickName: String) {
        viewModel.getRecordData(nickName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("recordData", it.toString())
                val playTime = it.playTime.replace("분", "")
                val startTime = it.startTime.substring(0, 10)
                binding.playTimeTxt.text = getTime(playTime.toInt()) + "시간"
                binding.startTimeTxt.text = startTime
            }, {
                Log.d("Error", it.message.toString())
            })
    }

    private fun getTime(time: Int): String {
        return (time / 60).toString()
    }
}