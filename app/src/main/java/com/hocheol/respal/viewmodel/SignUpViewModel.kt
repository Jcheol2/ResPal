package com.hocheol.respal.viewmodel

import android.util.Log
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.SignUpResponseDto
import com.hocheol.respal.repository.MainRepository
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
    private var TAG = this.javaClass.simpleName
    fun signUp(inputEmail: String, inputPassword: String, inputNickname: String, inputPicture: String,
               signUpCallback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val signUpInput: HashMap<String, Any?> = HashMap()
                signUpInput["email"] = inputEmail
                signUpInput["password"] = inputPassword
                signUpInput["picture"] = inputNickname
                signUpInput["nickname"] = inputPicture
                signUpInput["provider"] = "common"
                Log.d(TAG, "signUpInput : $signUpInput")
                val response: SignUpResponseDto = withContext(Dispatchers.IO) {
                    try {
                        mainRepository.signUp(signUpInput.toJsonRequestBody()).blockingGet()
                    } catch (e: HttpException) {
                        val errorCode = e.code()
                        Log.e(TAG, "HTTP Error Code: $errorCode")
                        throw e
                    }
                }
                signUpCallback(true)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                signUpCallback(false)
            }
        }
    }
}