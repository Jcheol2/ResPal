package com.hocheol.respal.data.local

import android.content.Context
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferenceStorage @Inject constructor(context: Context) {

    private val commonPref = context.getSharedPreferences("common", Context.MODE_PRIVATE)
    private val commonEditor = commonPref.edit()
    private val gson = Gson()

    fun saveString(key: String, value: String) {
        commonEditor.putString(key, value).apply()
    }
    fun getString(key: String): String? {
        return commonPref.getString(key, null)
    }

    fun saveAccessToken(aToken: String) {
        commonEditor.putString("accessToken", aToken).apply()
    }

    fun getAccessToken(): String? {
        return commonPref.getString("accessToken", null)
    }

    fun saveRefreshToken(rToken: String) {
        commonEditor.putString("refreshToken", rToken).apply()
    }

    fun getRefreshToken(): String? {
        return commonPref.getString("refreshToken", null)
    }
}