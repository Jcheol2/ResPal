package com.hocheol.respal.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.hocheol.respal.data.local.model.UserInfo
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "data_store_pref")
class DataStoreStorage @Inject constructor(private val context: Context) {
    private val TAG = this.javaClass.simpleName
    private val gson = Gson()

    private suspend fun saveString(key: String, value: String) {
        Log.i(TAG, "saveString($key, $value)")
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    private fun getString(key: String): Flow<String?> {
        Log.i(TAG, "getString($key)")
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferencesKey]
            }
    }

    suspend fun saveAccessToken(aToken: String) {
        saveString("accessToken", aToken)
    }

    suspend fun getAccessToken(): String? {
        return getString("accessToken").firstOrNull()
    }

    suspend fun saveRefreshToken(rToken: String) {
        saveString("refreshToken", rToken)
    }

    suspend fun getRefreshToken(): String? {
        return getString("refreshToken").firstOrNull()
    }

    suspend fun saveUserInfo(inputUserInfo: UserInfo) {
        val userInfoJson = gson.toJson(inputUserInfo)
        saveString("userInfo", userInfoJson)
    }

    suspend fun getUserInfo(): Flow<UserInfo?> = flow {
        val userInfoJson = getString("userInfo").firstOrNull()
        val userInfo = userInfoJson?.let {
            gson.fromJson(it, UserInfo::class.java)
        }
        emit(userInfo)
    }
}
