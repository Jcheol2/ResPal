package com.hocheol.respal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.view.MyResumeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private val TAG = this.javaClass.simpleName

    private val _fragmentToReplace = MutableLiveData<Fragment?>()
    val fragmentToReplace: LiveData<Fragment?> get() = _fragmentToReplace

    fun setFragmentToReplace(fragment: Fragment?) {
        _fragmentToReplace.postValue(fragment)
    }

    fun signInOauth(context: Context, platform: String) {
        val loginUrl: String
        val clientId: String
        val redirectUri: String
        var scopes = listOf<String>()

        when {
            platform.contains("kakao") -> {
                loginUrl = "https://kauth.kakao.com/oauth/authorize?"
                clientId = "6dee52527ff692975e9b7b8596ad76b5"
                redirectUri = "https://api.respal.me/oauth/app/login/kakao"
            }
            platform.contains("google") -> {
                loginUrl = "https://accounts.google.com/o/oauth2/auth?"
                clientId = "900804701090-sk6rt9ah5cp1tmg6ppudj48ki2hs29co.apps.googleusercontent.com"
                redirectUri = "https://api.respal.me/oauth/app/login/google"
                scopes = listOf("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile")

            }
            platform.contains("github") -> {
                loginUrl = "https://github.com/login/oauth/authorize?"
                clientId = "Iv1.dbc970eb37f92943"
                redirectUri = "https://api.respal.me/oauth/app/login/github"
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
    }

    fun sendOauthCallBack(code: String) = mainRepository.sendOauthCallBack(code)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ items ->
            // 여기서 액세스토큰 리프레시 토큰 저장 후 ME 화면으로 이동
            println(items)
            sharedPreferenceStorage.saveAccessToken(items.result.accessToken)
            sharedPreferenceStorage.saveRefreshToken(items.result.refreshToken)
            // 여기서 프래그먼트가 안바뀜 검토 필요
            setFragmentToReplace(MyResumeFragment())
        }, { e ->
            println(e.toString())
        })

    fun sendOauthSignUp(code: String) = mainRepository.sendOauthSignUp(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())  // 메인 스레드에서 UI 업데이트를 위해 수정
        .subscribe(
            { responseDto ->
                val requestInput: HashMap<String, Any?> = HashMap()
                requestInput["email"] = responseDto.result.userInfo.email
                requestInput["password"] = null
                requestInput["picture"] = responseDto.result.userInfo.image
                requestInput["nickname"] = responseDto.result.userInfo.nickname
                requestInput["provider"] = responseDto.result.provider

                val gson = Gson()
                val mediaType = "application/json".toMediaTypeOrNull()
                val json = gson.toJson(requestInput)
                val requestBody = RequestBody.create(mediaType, json)

                Log.d("정철", "requestInput : $requestInput")
                signUpOauth(requestBody)
            },
            { error ->
                error.printStackTrace()  // 에러 로그 출력

                val response = error as? HttpException
                if (response?.code() == 400) {
                    println("type 설정 에러")
                } else {
                    // 기타 에러 처리
                }
            }
        )

    private fun signUpOauth(requestBody: RequestBody) = mainRepository.signUpOauth(requestBody)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ items ->
            // 여기서 액세스토큰 리프레시 토큰 저장 후 ME 화면으로 이동
            println(items)
            sharedPreferenceStorage.saveAccessToken(items.result.accessToken)
            sharedPreferenceStorage.saveRefreshToken(items.result.refreshToken)
            setFragmentToReplace(MyResumeFragment())
        }, { e ->
            println(e.toString())
        })
}
