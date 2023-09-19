package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.SampleApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val githubApi: SampleApi
){
    fun getUserInfo(owner : String) = githubApi.getRepos(owner)
}