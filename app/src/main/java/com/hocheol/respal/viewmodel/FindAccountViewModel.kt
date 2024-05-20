package com.hocheol.respal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.remote.model.FindAccountResponseDto
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
class FindAccountViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private val TAG = this.javaClass.simpleName
    private val _responseEvent = SingleLiveEvent<Pair<String, Boolean>>()
    val responseEvent: LiveData<Pair<String, Boolean>> get() = _responseEvent

    fun findAccount(inputEmail: String, inputTmpPw: String) {
        coroutineScope.launch {
            try {
                val findAccountInput: HashMap<String, Any> = hashMapOf(
                    "email" to inputEmail,
                    "tmpPassword" to inputTmpPw
                )

                val response: FindAccountResponseDto = withContext(Dispatchers.IO) {
                    mainRepository.findAccount(findAccountInput.toJsonRequestBody())
                }

                _responseEvent.postValue(Pair("findAccount", true))
            } catch (e: HttpException) {
                val errorCode = e.code()
                Log.e(TAG, "HTTP Error Code: $errorCode")
                _responseEvent.postValue(Pair("findAccount", false))
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                _responseEvent.postValue(Pair("findAccount", false))
            }
        }
    }
}