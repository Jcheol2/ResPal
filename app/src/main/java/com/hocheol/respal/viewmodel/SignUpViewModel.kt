package com.hocheol.respal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.LoginResponseDto
import com.hocheol.respal.data.remote.model.SignUpResponseDto
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.SingleLiveEvent
import com.hocheol.respal.widget.utils.toJsonRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private val _responseEvent = SingleLiveEvent<Pair<String, Boolean>>()
    val responseEvent: LiveData<Pair<String, Boolean>> get() = _responseEvent

    fun signUp(inputEmail: String, inputPassword: String, inputNickname: String, inputPhoto: String) {
        coroutineScope.launch {
            try {
                val signUpInput: HashMap<String, Any> = hashMapOf(
                    "email" to inputEmail,
                    "password" to inputPassword,
                    "picture" to inputPhoto,
                    "nickname" to inputNickname,
                    "provider" to "common",
                )

                val response: SignUpResponseDto = withContext(Dispatchers.IO) {
                    mainRepository.signUp(signUpInput.toJsonRequestBody())
                }

                _responseEvent.postValue(Pair("signUp", true))
            } catch (e: HttpException) {
                val errorCode = e.code()
                Log.e(TAG, "HTTP Error Code: $errorCode")
                _responseEvent.postValue(Pair("signUp", false))
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                _responseEvent.postValue(Pair("signUp", false))
            }
        }
    }
}