package com.hocheol.respal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private val _responseEvent = SingleLiveEvent<Pair<String, Boolean>>()
    val responseEvent: LiveData<Pair<String, Boolean>> get() = _responseEvent

    fun logout() {
        coroutineScope.launch {
            try {
                val response: ResponseBody = withContext(Dispatchers.IO) {
                    mainRepository.logout()
                }

                _responseEvent.postValue(Pair("logout", true))
            } catch (e: HttpException) {
                val errorCode = e.code()
                Log.e(TAG, "HTTP Error Code: $errorCode")
                _responseEvent.postValue(Pair("logout", false))
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                _responseEvent.postValue(Pair("logout", false))
            }
        }
    }
}