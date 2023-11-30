package com.hocheol.respal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

//    fun getUserInfo(owner: String) = mainRepository.getUserInfo(owner)
//        .subscribeOn(Schedulers.io())
//        .observeOn(Schedulers.io())
//        .subscribe({ items ->
//            items.forEach { println(it) }
//        }, { e ->
//            println(e.toString())
//        })

    fun signInOauth(context: Context, platform: String) {
        // 로그인에 필요한 파라미터 설정
        var clientId = ""
        var redirectUri = ""
        var loginUrl = ""
        var scopes = listOf<String>()
        val urlScheme = "app"
        var type = ""

        when {
            platform.contains("kakao") -> {
                clientId = "6dee52527ff692975e9b7b8596ad76b5"
                redirectUri = "http://api-respal.me/oauth/app/login/kakao"
                loginUrl = "https://kauth.kakao.com/oauth/authorize?" +
                        "client_id=6dee52527ff692975e9b7b8596ad76b5&redirect_uri=http://api-respal.me/oauth/app/login/kakao&response_type=code"
            }
            platform.contains("google") -> {
                clientId = "900804701090-sk6rt9ah5cp1tmg6ppudj48ki2hs29co.apps.googleusercontent.com"
                redirectUri = "http://api-respal.me/oauth/app/login/google"
                loginUrl = "https://accounts.google.com/o/oauth2/auth?" +
                        "client_id=900804701090-sk6rt9ah5cp1tmg6ppudj48ki2hs29co.apps.googleusercontent.com&redirect_uri=http://api-respal.me/oauth/app/login/google&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
                scopes = listOf("email", "profile")
            }
            platform.contains("github") -> {
                clientId = "Iv1.dbc970eb37f92943"
                redirectUri = "http://api-respal.me/oauth/app/login/github"
                loginUrl = "https://github.com/login/oauth/authorize?" +
                        "client_id=Iv1.dbc970eb37f92943&redirect_uri=http://api-respal.me/oauth/app/login/github"
            }
            else -> return
        }

        // 로그인 요청 URL 생성
        val authUrl = Uri.parse(loginUrl).buildUpon()
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("redirect_uri", redirectUri)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scopes.joinToString(" "))
            .build()

        Log.d("TEST", authUrl.path.toString())

//        // 웹뷰를 통해 로그인 페이지 열기
//        val result = FlutterWebAuth.authenticate(
//                    context,
//            authUrl.toString(),
//            urlScheme
//        )
//
//        // 콜백 데이터 처리
//        val uri = Uri.parse(result)
//        val prefix = "app://"
//        val path = result.substring(prefix.length)
//        val code = uri.getQueryParameter("uid")
//
//        type = if (path.startsWith("signup")) {
//            "signup"
//        } else if (path.startsWith("callback")) {
//            "callback"
//        } else {
//            print("에러")
//            return
//        }

//        sendOauthToBackend(context, type, code)
    }
}
