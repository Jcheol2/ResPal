package com.hocheol.respal.view.viewpager

import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentViewPagerBinding
import com.hocheol.respal.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>(R.layout.fragment_view_pager) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mainViewModel.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.viewPager.adapter = MainPagerAdapter(childFragmentManager)
        binding.viewPager.offscreenPageLimit = 3
        initObserve()
    }

    private fun initObserve() {
        mainViewModel.currentViewPagerPosition.observe(this) { position ->
            binding.viewPager.currentItem = position
        }
    }
}