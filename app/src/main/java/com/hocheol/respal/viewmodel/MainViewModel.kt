package com.hocheol.respal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getUserInfo(owner: String) = mainRepository.getUserInfo(owner)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe({ items ->
            items.forEach { println(it) }
        }, { e ->
            println(e.toString())
        })

}