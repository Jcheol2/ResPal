package com.hocheol.respal.repository

import com.hocheol.respal.data.remote.api.SampleApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val sampleApi: SampleApi
){
    fun getUserInfo(owner : String) = sampleApi.getRepos(owner)
    fun getReposNotRX(owner : String) = sampleApi.getReposNotRX(owner)
}