package com.hocheol.respal.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hocheol.respal.view.ActivityFragment
import com.hocheol.respal.view.MyResumeFragment
import com.hocheol.respal.view.SettingsFragment

class MainPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MyResumeFragment()
            1 -> ActivityFragment()
            else -> SettingsFragment()
        }
    }
    override fun getCount() = 3
}