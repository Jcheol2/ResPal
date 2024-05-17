package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.RespalApi
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val respalApi: RespalApi
){
    fun test() = respalApi.test()
    fun signUp(requestBody: RequestBody) = respalApi.signUp(requestBody)
    fun emailAuth(inputEmail: String) = respalApi.emailAuth(inputEmail)
    fun login(requestBody: RequestBody) = respalApi.login(requestBody)
    fun findAccount(requestBody: RequestBody) = respalApi.findAccount(requestBody)
    fun changePassword(requestBody: RequestBody) = respalApi.changePassword(requestBody)
    fun logout() = respalApi.logout()
    fun oauthLogin(provider: String, code: String) = respalApi.oauthLogin(provider, code)
    fun requestOauthInfo(uid: String, type: String) = respalApi.requestOauthInfo(uid, type)
    fun searchMembers(searchWord: String, limit: Int) = respalApi.searchMembers(searchWord, limit)

    fun deleteTag(taggedMembersId: String) = respalApi.deleteTag(taggedMembersId)
    fun createTag(resumeId: String) = respalApi.createTag(resumeId)

    fun findMyResume() = respalApi.findMyResume()
    fun findHubResume() = respalApi.findHubResume()
    fun findTagResume() = respalApi.findTagResume()
}