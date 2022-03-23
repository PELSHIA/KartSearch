package com.hcraestrak.kartsearch.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hcraestrak.kartsearch.view.fragment.*

class InformationViewPagerAdapter(fragmentActivity: FragmentActivity, val id: String, val type: String): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserPercentFragment(id, type)
            1 -> UserAvgRankFragment(id, type)
            else -> UserTrackStatFragment(id, type)
        }
    }
}