package com.hocheol.respal.viewmodel

import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.data.local.model.UserInfo
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MyResumeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    // TODO: Implement the ViewModel

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

    fun findResume() = mainRepository.findMyResume()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ response ->
            println(response)
        }, { e ->
            println(e.toString())
        })
}