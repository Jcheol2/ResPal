package com.hocheol.respal.data.remote.api

import com.hocheol.respal.data.remote.model.SampleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RespalApi {
//    @GET("users/{owner}/repos")
//    fun getRepos(@Path("owner") owner: String) : Single<List<SampleResponse>>

    /** Oauth 정보를 서버로 보내 이미 회원인지 아닌지 판별 */
    @GET("oauth/user/repos/{code}/?type={type}")
    fun sendOauthInfo(@Path("code") code: String, type:String) : Single<List<SampleResponse>>

    /** 회원가입 */
    @POST("member/join")
    fun signUpOauth() : Single<List<SampleResponse>>

    /** 로그인 */
    @POST("member/login")
    fun login() : Single<List<SampleResponse>>

    /** 로그아웃 */
    @POST("member/logout")
//    @Header("Bearer")
    fun logout() : Single<List<SampleResponse>>

    /** TEST */
    @GET("test")
//    @Header("Bearer")
    fun test() : Single<List<SampleResponse>>

}