package com.hocheol.respal.view

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseActivity
import com.hocheol.respal.databinding.ActivityMainBinding
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Constants.ACTIVITY_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.LOGIN_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.MY_RESUME_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.SETTINGS_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = this.javaClass.simpleName
    private val mainViewModel by viewModels<MainViewModel>()

    override fun init() {
        binding.activity = this
        mainViewModel.init(supportFragmentManager)
        mainViewModel.openFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        handleIntent(intent)

        mainViewModel.currentFragment.observe(this) { currentFragment ->
            if (currentFragment == null) return@observe
            if (currentFragment.tag!!.contains("LOGIN")) {
                binding.toolbarView.visibility = View.GONE
                binding.bottomNavView.visibility = View.GONE
                binding.frameLayout.visibility = View.VISIBLE
                binding.viewPager.visibility = View.GONE
                binding.viewPager.currentItem = 0 // viewPager 초기화
            } else {
                binding.frameLayout.visibility = View.GONE
                binding.viewPager.visibility = View.VISIBLE
                binding.toolbarView.visibility = View.VISIBLE
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            val fragmentTag = when (menuItem.itemId) {
                R.id.myFragment -> MY_RESUME_FRAGMENT_TAG
                R.id.activityFragment -> ACTIVITY_FRAGMENT_TAG
                R.id.settingsFragment -> SETTINGS_FRAGMENT
                else -> return@setOnItemSelectedListener false
            }
            val pageIndex = when (menuItem.itemId) {
                R.id.myFragment -> 0
                R.id.activityFragment -> 1
                R.id.settingsFragment -> 2
                else -> return@setOnItemSelectedListener false
            }
            changeBottomNavigationRes(fragmentTag)
            binding.viewPager.currentItem = pageIndex
            true
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNav.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        binding.viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        binding.viewPager.offscreenPageLimit = 3
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                Log.d(TAG, "Received URI: $uri")
                val prefix = "app://"
                val path = uri.toString().substring(prefix.length)
                val uid = uri.getQueryParameter("uid")
                val type = if (path.startsWith("signup")) {
                    "signup"
                } else {
                    "callback"
                }
                Log.e("TEST", "uid : $uid")
                if (!uid.isNullOrEmpty()) {
                    mainViewModel.requestOauthInfo(uid, type) { result ->
                        if (result) {
                            Log.d("정철", "success")
                        } else {
                            Log.d("정철", "fail")
                        }
                    }
                }
            }
        }
    }

    private fun changeBottomNavigationRes(tag: String) {
        binding.bottomNav.menu.findItem(R.id.myFragment).setIcon(R.drawable.ic_my)
        binding.bottomNav.menu.findItem(R.id.activityFragment).setIcon(R.drawable.ic_activity)
        binding.bottomNav.menu.findItem(R.id.settingsFragment).setIcon(R.drawable.ic_settings)
        when (tag) {
            MY_RESUME_FRAGMENT_TAG -> {
                binding.bottomNav.menu.findItem(R.id.myFragment).setIcon(R.drawable.ic_my_selected)
            }
            ACTIVITY_FRAGMENT_TAG -> {
                binding.bottomNav.menu.findItem(R.id.activityFragment).setIcon(R.drawable.ic_activity_selected)
            }
            SETTINGS_FRAGMENT -> {
                binding.bottomNav.menu.findItem(R.id.settingsFragment).setIcon(R.drawable.ic_settings_selected)
            }
        }
    }
}