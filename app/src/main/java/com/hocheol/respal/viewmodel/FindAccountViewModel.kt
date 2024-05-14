package com.hocheol.respal.viewmodel

import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindAccountViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    private var TAG = this.javaClass.simpleName
    fun findAccount(email: String, tmpPassword: String,
                    callback: (Boolean) -> Unit) {
        coroutineScope.launch {

        }
    }
}