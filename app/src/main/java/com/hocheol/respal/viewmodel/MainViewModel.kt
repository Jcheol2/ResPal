package com.hocheol.respal.viewmodel

import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    fun getUserInfo(owner: String) = mainRepository.getUserInfo(owner)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ items ->
            items.forEach { println(it) }
        }, { e ->
            println(e.toString())
        })

    // RX를 사용하지 않고 coroutine을 사용하는 예시이지만 아직 안됨
    fun getUserInfo1(owner: String) {
        try {
            val items = mainRepository.getReposNotRX(owner)
            for (item in items) {
                println(item)
            }
        } catch (e: Exception) {
            println(e.toString())
        }
    }

}