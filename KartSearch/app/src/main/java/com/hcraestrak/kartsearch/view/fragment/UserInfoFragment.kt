package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserInfoBinding
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.UserInfoViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getProfileData(nickName)
        viewModel.infoData.observe(viewLifecycleOwner, {
            Glide.with(binding.levelImg.context).load(it.levelImg).into(binding.levelImg)
            Glide.with(binding.userProfile.context).load(it.profileImg).into(binding.userProfile)
            binding.clubTxt.text = if (it.club != "가입된클럽이없습니다") it.club else "없음"
            binding.levelTxt.text = "Lv.$level"
        })
    }

    private fun setRecordData(nickName: String) {
        viewModel.getRecordData(nickName)
        viewModel.recordData.observe(viewLifecycleOwner, {
            val playTime = it.playTime.replace("분", "")
            binding.playTimeTxt.text = getTime(playTime.toInt()) + "시간"
            binding.startTimeTxt.text = it.startTime.substring(0, 10)
        })
    }

    private fun getTime(time: Int): String {
        return (time / 60).toString()
    }
}