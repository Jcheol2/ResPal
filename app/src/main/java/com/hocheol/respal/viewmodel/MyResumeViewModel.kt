package com.hocheol.respal.viewmodel

import android.util.Log
import com.google.gson.JsonObject
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.local.model.UserInfo
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyResumeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private var TAG = this.javaClass.simpleName

    fun getUserInfo(): UserInfo? {
        return sharedPreferenceStorage.getUserInfo()
    }

    fun getTest() = mainRepository.test()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ response ->
            println(response)
        }, { e ->
            println(e.toString())
        })

    fun findResume(findResumeCallback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val response: JsonObject = withContext(Dispatchers.IO) {
                    try {
                        mainRepository.findMyResume().blockingGet()
                    } catch (e: HttpException) {
                        val errorCode = e.code()
                        Log.e(TAG, "HTTP Error Code: $errorCode")
                        throw e
                    }
                }
                findResumeCallback(true)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                findResumeCallback(false)
            }
        }
    }
}