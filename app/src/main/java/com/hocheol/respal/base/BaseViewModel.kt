package com.hocheol.respal.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("**COROUTINE_EXCEPTION**", throwable.stackTrace.toString())
    }

    protected val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO) + exceptionHandler
}