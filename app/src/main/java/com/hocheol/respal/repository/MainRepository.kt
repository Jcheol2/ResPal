package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.RespalApi
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val respalApi: RespalApi
){
    suspend fun test() = respalApi.test()
    suspend fun signUp(requestBody: RequestBody) = respalApi.signUp(requestBody)
    suspend fun emailAuth(inputEmail: String) = respalApi.emailAuth(inputEmail)
    suspend fun login(requestBody: RequestBody) = respalApi.login(requestBody)
    suspend fun findAccount(requestBody: RequestBody) = respalApi.findAccount(requestBody)
    suspend fun changePassword(requestBody: RequestBody) = respalApi.changePassword(requestBody)
    suspend fun logout() = respalApi.logout()
    suspend fun oauthLogin(provider: String, code: String) = respalApi.oauthLogin(provider, code)
    suspend fun requestOauthInfo(uid: String, type: String) = respalApi.requestOauthInfo(uid, type)
    suspend fun searchMembers(searchWord: String, limit: Int) = respalApi.searchMembers(searchWord, limit)

    suspend fun deleteTag(taggedMembersId: String) = respalApi.deleteTag(taggedMembersId)
    suspend fun createTag(resumeId: String) = respalApi.createTag(resumeId)

    suspend fun findMyResume() = respalApi.findMyResume()
    suspend fun findHubResume() = respalApi.findHubResume()
    suspend fun findTagResume() = respalApi.findTagResume()
}