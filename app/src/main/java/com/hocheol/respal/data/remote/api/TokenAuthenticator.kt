package com.hocheol.respal.data.remote.api

import android.util.Log
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.RefreshTokenResponseDto
import com.hocheol.respal.repository.MainRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : Authenticator {
    private val TAG = this.javaClass.simpleName
    private var isRefreshingToken = false
    override fun authenticate(route: Route?, response: Response): Request? {
        if (isRefreshingToken) {
            Log.d(TAG, "이미 토큰 재발행 시도 중 .. 이번 요청 무시")
            return null
        }

        isRefreshingToken = true
        Log.d(TAG, "토큰 재발행 시도 중 ..")
        return if (fetchNewAccessToken()) {
            val newAccessToken = sharedPreferenceStorage.getAccessToken()
            Log.d(TAG, "토큰 재발행 성공!! : $newAccessToken")
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        } else {
            Log.d(TAG, "토큰 재발행 실패 ..")
            null
        }
    }

    private fun fetchNewAccessToken(): Boolean {
        return runBlocking {
            try {
                sharedPreferenceStorage.saveAccessToken("")
                if (sharedPreferenceStorage.getRefreshToken() != null) {
                    val response = sendServer(sharedPreferenceStorage.getRefreshToken()!!)
                    if (response != null) {
                        sharedPreferenceStorage.saveAccessToken(response.result.accessToken)
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun sendServer(refreshToken: String): RefreshTokenResponseDto? {
        try {
            val response = mainRepository.reAuth(refreshToken)
            return response.blockingGet()
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}")
            e.printStackTrace()
        }
        return null
    }
}