package com.hocheol.respal.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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