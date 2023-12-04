package com.hocheol.respal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    private val TAG = this.javaClass.simpleName
    private val _webViewPath = MutableLiveData<String?>()
    val webViewPath: LiveData<String?> get() = _webViewPath

//    fun getUserInfo(owner: String) = mainRepository.getUserInfo(owner)
//        .subscribeOn(Schedulers.io())
//        .observeOn(Schedulers.io())
//        .subscribe({ items ->
//            items.forEach { println(it) }
//        }, { e ->
//            println(e.toString())
//        })

    fun signInOauth(context: Context, platform: String) {
        val loginUrl: String
        val clientId: String
        val redirectUri: String
        var scopes = listOf<String>()

        when {
            platform.contains("kakao") -> {
                loginUrl = "https://kauth.kakao.com/oauth/authorize?"
                clientId = "6dee52527ff692975e9b7b8596ad76b5"
                redirectUri = "http://api-respal.me/oauth/app/login/kakao"
            }
            platform.contains("google") -> {
                loginUrl = "https://accounts.google.com/o/oauth2/auth?"
                clientId = "900804701090-sk6rt9ah5cp1tmg6ppudj48ki2hs29co.apps.googleusercontent.com"
                redirectUri = "http://api-respal.me/oauth/app/login/google"
                scopes = listOf("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")

            }
            platform.contains("github") -> {
                loginUrl = "https://github.com/login/oauth/authorize?"
                clientId = "Iv1.dbc970eb37f92943"
                redirectUri = "http://api-respal.me/oauth/app/login/github"
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
                .setToolbarColor(ContextCompat.getColor(context, R.color.primary))
                .build()
            customTabsIntent.intent.data = authUrl

            context.startActivity(customTabsIntent.intent, customTabsIntent.startAnimationBundle)
        } catch (e: Exception) {
            Log.d(TAG, "error: $e")
        }

//        sendOauthToBackend(context, type, code)
    }
}
