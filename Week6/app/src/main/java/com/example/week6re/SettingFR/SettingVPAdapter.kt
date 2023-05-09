package com.example.week6re.SettingFR

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.week6re.HomeFR.OneFragment
import com.example.week6re.HomeFR.ThreeFragment
import com.example.week6re.HomeFR.TwoFragment

class SettingVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SettingOneFragment()
            1 -> SettingTwoFragment()
            2 -> SettingThreeFragment()
            else -> SettingOneFragment()
        }
    }
}