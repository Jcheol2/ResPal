package com.hocheol.respal.data.remote.api

import com.hocheol.respal.data.remote.model.SampleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SampleApi {
    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner: String) : Single<List<SampleResponse>>

    @GET("users/{owner}/repos")
    fun getReposNotRX(@Path("owner") owner: String) : List<SampleResponse>
}