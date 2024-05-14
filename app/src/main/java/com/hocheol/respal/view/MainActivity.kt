package com.hocheol.respal.view

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseActivity
import com.hocheol.respal.databinding.ActivityMainBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Constants.LOGIN_FRAGMENT_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = this.javaClass.simpleName
    private val mainViewModel by viewModels<MainViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

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
            } else {
                binding.toolbarView.visibility = View.VISIBLE
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                Log.d(TAG, "Received URI: $uri")
                val prefix = "app://"
                val path = uri.toString().substring(prefix.length)
                val code = uri.getQueryParameter("uid")
                longShowToast("code : $code")
                if (path.startsWith("signup")) {
                    code?.let { mainViewModel.sendOauthSignUp(it) { result ->
                        if (result) {
                            Log.d("정철", "success")
                        } else {
                            Log.d("정철", "fail")
                        }
                    } }
                } else if (path.startsWith("callback")) {
                    code?.let { mainViewModel.sendOauthCallBack(it) { result ->
                        if (result) {
                            Log.d("정철", "success")
                        } else {
                            Log.d("정철", "fail")
                        }
                    } }
                } else {
                    print("Oauth error")
                    return
                }
            }
        }
    }

    // Github, Kakao Callback
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let { uri ->
            Log.d(TAG, "Received URI: $uri")
            val prefix = "app://"
            val path = uri.toString().substring(prefix.length)
            val code = uri.getQueryParameter("uid")
            longShowToast("code : $code")
            if (path.startsWith("signup")) {
                code?.let { mainViewModel.sendOauthSignUp(it) { result ->
                    if (result) {
                        Log.d("정철", "success")
                    } else {
                        Log.d("정철", "fail")
                    }
                } }
            } else if (path.startsWith("callback")) {
                code?.let { mainViewModel.sendOauthCallBack(it) { result ->
                    if (result) {
                        Log.d("정철", "success")
                    } else {
                        Log.d("정철", "fail")
                    }
                } }
            } else {
                print("Oauth error")
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "++ onResume ++")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "++ onPause ++")
    }
}