package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.RespalApi
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val respalApi: RespalApi
){
    fun sendOauthSignUp(code: String) = respalApi.sendOauthSignUp(code)
    fun sendOauthCallBack(code: String) = respalApi.sendOauthCallBack(code)
    fun signUp(requestBody: RequestBody) = respalApi.signUp(requestBody)
    fun login(requestBody: RequestBody) = respalApi.login(requestBody)
    fun logout() = respalApi.logout()
    fun findAccount(requestBody: RequestBody) = respalApi.findAccount(requestBody)
    fun test() = respalApi.test()
    fun reAuth(refreshToken: String) = respalApi.reAuth(refreshToken)
    fun findMyResume() = respalApi.findMyResume()
    fun findHubResume() = respalApi.findHubResume()
    fun findTagResume() = respalApi.findTagResume()

}