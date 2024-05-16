package com.hocheol.respal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.Constants.GITHUB
import com.hocheol.respal.widget.utils.Constants.GOOGLE
import com.hocheol.respal.widget.utils.Constants.KAKAO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private val TAG = this.javaClass.simpleName

    fun signInOauth(context: Context, platform: String) {
        sharedPreferenceStorage.saveAccessToken("") // 로그인 진행 전 토큰 비우기
        val loginUrl: String
        val clientId: String
        val redirectUri: String
        var scopes = listOf<String>()

        when {
            platform.contains(GITHUB) -> {
                loginUrl = "https://github.com/login/oauth/authorize?"
                clientId = "Iv1.dbc970eb37f92943"
                redirectUri = "https://api.respal.me/oauth/app/login/github"
            }
            platform.contains(GOOGLE) -> {
                loginUrl = "https://accounts.google.com/o/oauth2/auth?"
                clientId = "900804701090-sk6rt9ah5cp1tmg6ppudj48ki2hs29co.apps.googleusercontent.com"
                redirectUri = "https://api.respal.me/oauth/app/login/google"
                scopes = listOf("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")

            }
            platform.contains(KAKAO) -> {
                loginUrl = "https://kauth.kakao.com/oauth/authorize?"
                clientId = "6dee52527ff692975e9b7b8596ad76b5"
                redirectUri = "https://api.respal.me/oauth/app/login/kakao"
            }
            else -> return
        }
        val authUrl: Uri = Uri.parse(loginUrl)
            .buildUpon()
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scopes.joinToString(" "))
            .build()

        Log.d(TAG, "authUrl: $authUrl")

        try {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(false)
                .setToolbarColor(ContextCompat.getColor(context, R.color.color_3B3B3B))
                .build()
            customTabsIntent.intent.data = authUrl

            context.startActivity(customTabsIntent.intent, customTabsIntent.startAnimationBundle)
        } catch (e: Exception) {
            Log.d(TAG, "error: $e")
        }
    }
}
