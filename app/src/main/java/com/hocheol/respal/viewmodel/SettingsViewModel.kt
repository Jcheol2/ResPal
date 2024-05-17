package com.hocheol.respal.viewmodel

import android.util.Log
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.LogoutResponseDto
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private var TAG = this.javaClass.simpleName
    fun logout(logoutCallback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val response: LogoutResponseDto = withContext(Dispatchers.IO) {
                    try {
                        mainRepository.logout().blockingGet()
                    } catch (e: HttpException) { // 응답이 비어서 NoSuchElementException 에러 발생
                        val errorCode = e.code()
                        Log.e(TAG, "HTTP Error Code: $errorCode")
                        throw e
                    }
                }
                logoutCallback(true)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                logoutCallback(false)
            }
        }
    }
}