package com.hocheol.respal.view

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseActivity
import com.hocheol.respal.databinding.ActivityMainBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val TAG = this.javaClass.simpleName
    private val mainViewModel by viewModels<MainViewModel>() // by viewModels()을 사용하여 Hilt가 생성한 ViewModel 인스턴스를 획득
    private val loginViewModel by viewModels<LoginViewModel>()

    // onCreate는 BaseActivity에서 정의했으므로 init이 거의 onCreate인 셈
    override fun init() {
        binding.activity = this
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, LoginFragment(), "LOGIN_FRAGMENT_TAG")
            .addToBackStack(null)
            .commitAllowingStateLoss()

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            intent.data?.let { uri ->
                Log.d(TAG, "Received URI: $uri")
                val prefix = "app://"
                val path = uri.toString().substring(prefix.length)
                val code = uri.getQueryParameter("uid")

                val type = if (path.startsWith("signup")) {
                    "signup"
                } else if (path.startsWith("callback")) {
                    "callback"
                } else {
                    print("Oauth error")
                    return
                }
                Log.d(TAG, type)
                Log.d(TAG, code.toString())
                longShowToast("$type, $code")
                loginViewModel.sendOauthToBackend(code!!, type)
            }
        }
    }

    // Github, Kakao Callback
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "여기들어옴")
        intent?.data?.let { uri ->
            Log.d(TAG, "Received URI: $uri")
            val prefix = "app://"
            val path = uri.toString().substring(prefix.length)
            val code = uri.getQueryParameter("uid")

            val type = if (path.startsWith("signup")) {
                "signup"
            } else if (path.startsWith("callback")) {
                "callback"
            } else {
                print("Oauth 에러")
                return
            }
            Log.d(TAG, type)
            Log.d(TAG, code.toString())
            longShowToast("$type, $code")
            loginViewModel.sendOauthToBackend(code!!, type)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "++onResume++")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "++onPause++")
    }
}