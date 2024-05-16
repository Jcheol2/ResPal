package com.hocheol.respal.viewmodel

import android.util.Log
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.FindAccountResponseDto
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.toJsonRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FindAccountViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private var TAG = this.javaClass.simpleName
    fun findAccount(inputEmail: String, inputTmpPw: String,
                    findAccountCallback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val findAccountInput: HashMap<String, Any> = HashMap()
                findAccountInput["email"] = inputEmail
                findAccountInput["tmpPassword"] = inputTmpPw
                Log.d(TAG, "findAccountInput : $findAccountInput")
                val response: FindAccountResponseDto = withContext(Dispatchers.IO) {
                    try {
                        mainRepository.findAccount(findAccountInput.toJsonRequestBody()).blockingGet()
                    } catch (e: HttpException) {
                        val errorCode = e.code()
                        Log.e(TAG, "HTTP Error Code: $errorCode")
                        throw e
                    }
                }
                findAccountCallback(true)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                findAccountCallback(false)
            }
        }
    }
}