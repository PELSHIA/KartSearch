package com.hcraestrak.kartsearch.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hcraestrak.kartsearch.R
import com.hcraestrak.kartsearch.databinding.FragmentUserInfoBinding
import com.hcraestrak.kartsearch.databinding.FragmentUserRecordBinding
import com.hcraestrak.kartsearch.view.base.BaseFragment
import com.hcraestrak.kartsearch.viewModel.MatchViewModel
import com.hcraestrak.kartsearch.viewModel.UserInfoViewModel
import com.hcraestrak.kartsearch.viewModel.UserViewModel
import com.hcraestrak.kartsearch.viewModel.data.InfoData
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
}