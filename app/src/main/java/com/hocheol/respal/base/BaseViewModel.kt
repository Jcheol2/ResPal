package com.hocheol.respal.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {
    // LiveData, StateFlow 등을 여기에 정의

    // CoroutineExceptionHandler를 사용하여 에러 처리
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // 에러 처리 로직
    }

    // 비동기 작업을 처리하는 CoroutineScope
    protected val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO) + exceptionHandler

    // 공통 함수 정의
    fun performCommonAction() {
        coroutineScope.launch {
            // 공통 동작 수행
        }
    }
}