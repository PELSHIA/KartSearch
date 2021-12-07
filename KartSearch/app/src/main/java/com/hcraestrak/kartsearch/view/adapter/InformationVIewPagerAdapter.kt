package com.hcraestrak.kartsearch.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hcraestrak.kartsearch.view.fragment.InformationFragment
import com.hcraestrak.kartsearch.view.fragment.UserInfoFragment
import com.hcraestrak.kartsearch.view.fragment.UserRecordFragment
import com.hcraestrak.kartsearch.view.fragment.UserStatsFragment

class InformationVIewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserRecordFragment()
            1 -> UserStatsFragment()
            else -> UserInfoFragment()
        }
    }
}