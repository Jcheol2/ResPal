package com.hocheol.respal.view

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
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
    private val mainViewModel by viewModels<MainViewModel>()

    override fun init() {
        binding.activity = this
        mainViewModel.init(supportFragmentManager)
        mainViewModel.openFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        handleIntent(intent)

        mainViewModel.currentFragmentTag.observe(this) { currentFragmentTag ->
            if (currentFragmentTag == null) return@observe
            Log.e(TAG, "Current Fragment : $currentFragmentTag")
            if (currentFragmentTag.contains("LOGIN")) {
                binding.toolbarView.visibility = View.GONE
                binding.bottomNavView.visibility = View.GONE
            } else {
                binding.toolbarView.visibility = View.VISIBLE
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            val pageIndex = when (menuItem.itemId) {
                R.id.myFragment -> 0
                R.id.activityFragment -> 1
                R.id.settingsFragment -> 2
                else -> return@setOnItemSelectedListener false
            }
            changeBottomNavigationRes(pageIndex)
            mainViewModel.onPageSelected(pageIndex)
            true
        }

        initObserve()
    }

    private fun initObserve() {
        mainViewModel.currentViewPagerPosition.observe(this) { position ->
            binding.bottomNav.menu.getItem(position).isChecked = true
            changeBottomNavigationRes(position)
        }
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

    private fun changeBottomNavigationRes(pageIndex: Int) {
        binding.bottomNav.menu.findItem(R.id.myFragment).setIcon(R.drawable.ic_my)
        binding.bottomNav.menu.findItem(R.id.activityFragment).setIcon(R.drawable.ic_activity)
        binding.bottomNav.menu.findItem(R.id.settingsFragment).setIcon(R.drawable.ic_settings)
        when (pageIndex) {
            0 -> {
                binding.bottomNav.menu.findItem(R.id.myFragment).setIcon(R.drawable.ic_my_selected)
            }
            1 -> {
                binding.bottomNav.menu.findItem(R.id.activityFragment).setIcon(R.drawable.ic_activity_selected)
            }
            2 -> {
                binding.bottomNav.menu.findItem(R.id.settingsFragment).setIcon(R.drawable.ic_settings_selected)
            }
        }
    }
}