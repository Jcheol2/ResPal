package com.hocheol.respal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.DataStoreStorage
import com.hocheol.respal.data.local.model.UserInfo
import com.hocheol.respal.data.remote.model.LoginResponseDto
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.Constants.GITHUB
import com.hocheol.respal.widget.utils.Constants.GOOGLE
import com.hocheol.respal.widget.utils.Constants.KAKAO
import com.hocheol.respal.widget.utils.SingleLiveEvent
import com.hocheol.respal.widget.utils.toJsonRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dataStoreStorage: DataStoreStorage
) : BaseViewModel() {
    private val _responseEvent = SingleLiveEvent<Pair<String, Boolean>>()
    val responseEvent: LiveData<Pair<String, Boolean>> get() = _responseEvent

    fun commonLogin(inputEmail: String, inputPw: String) {
        coroutineScope.launch {
            dataStoreStorage.saveAccessToken("")
            try {
                val loginInput: HashMap<String, Any> = hashMapOf(
                    "email" to inputEmail,
                    "password" to inputPw
                )

                val response: LoginResponseDto = withContext(Dispatchers.IO) {
                    mainRepository.login(loginInput.toJsonRequestBody())
                }

                dataStoreStorage.saveAccessToken(response.result.accessToken)
                dataStoreStorage.saveRefreshToken(response.result.refreshToken)
                dataStoreStorage.saveUserInfo(
                    UserInfo(
                        inputEmail,
                        inputPw,
                        "",
                        "",
                        "common"
                    )
                )
                _responseEvent.postValue(Pair("commonLogin", true))
            } catch (e: HttpException) {
                val errorCode = e.code()
                Log.e(TAG, "HTTP Error Code: $errorCode")
                _responseEvent.postValue(Pair("commonLogin", false))
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                _responseEvent.postValue(Pair("commonLogin", false))
            }
        }
    }

    fun signInOauth(context: Context, platform: String) {
        runBlocking { dataStoreStorage.saveAccessToken("") }
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
