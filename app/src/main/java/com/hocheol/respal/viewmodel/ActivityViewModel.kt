package com.hocheol.respal.viewmodel

import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

}