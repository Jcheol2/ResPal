package com.hocheol.respal.data.remote.api

import android.util.Log
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.widget.utils.Constants.BASE_URL
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    // 주입 순서 때문에 MainRepository 넣을 경우 에러 발생
    /*private val mainRepository: MainRepository,*/
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : Authenticator {
    private val TAG = this.javaClass.simpleName
    private var isRefreshingToken = false
    override fun authenticate(route: Route?, response: Response): Request? {
        if (isRefreshingToken) {
            Log.d(TAG, "이미 토큰 재발행 시도 중 .. 이번 요청 무시")
            return null
        } else {
            Log.d(TAG, "토큰 재발행 시작 ..")
            isRefreshingToken = true
        }

        return if (fetchNewAccessToken()) {
            Log.d(TAG, "토큰 재발행 성공 !!")
            val newAccessToken = sharedPreferenceStorage.getAccessToken()
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
                val refreshToken = sharedPreferenceStorage.getRefreshToken()
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("$BASE_URL/jwt/refresh")
                    .header("Authorization", "Bearer $refreshToken")
                    .post(RequestBody.create("application/json".toMediaTypeOrNull(), "{}"))
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val result = jsonResponse.getJSONObject("result")
                        val newAccessToken = result.getString("accessToken")
                        sharedPreferenceStorage.saveAccessToken(newAccessToken)
                        return@runBlocking true
                    }
                } else {
                    Log.e(TAG, "토큰 갱신 실패: ${response.code}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "토큰 갱신 중 오류 발생: ${e.message}")
                e.printStackTrace()
            }
            return@runBlocking false
        }
    }
}