package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.RespalApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val respalApi: RespalApi
){
//    fun getUserInfo(owner: String) = respalApi.getRepos(owner)
    fun sendOauthInfo(code: String, type: String) = respalApi.sendOauthInfo(code, type)
    fun signUpOauth() = respalApi.signUpOauth()
    fun login() = respalApi.login()
    fun logout() = respalApi.logout()
    fun test() = respalApi.test()
}