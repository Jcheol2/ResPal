package com.hocheol.respal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private var TAG = this.javaClass.simpleName

    private val _logoutResponse = MutableLiveData<Boolean>()
    val logoutResponse: LiveData<Boolean> get() = _logoutResponse

    fun logout() {
        coroutineScope.launch {
            try {
                mainRepository.logout().subscribe(
                    { response ->
                        _logoutResponse.postValue(true)
                    },
                    { error ->
                        Log.e(TAG, "Error during logout: ${error.message}")
                        _logoutResponse.postValue(false)
                    }
                )
            } catch (e: Exception) {
                _logoutResponse.postValue(false)
                Log.e(TAG, "Logout failed", e)
            }
        }
    }
}