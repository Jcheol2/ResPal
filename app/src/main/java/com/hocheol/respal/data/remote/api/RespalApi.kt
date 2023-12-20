package com.hocheol.respal.data.remote.api

import com.hocheol.respal.data.remote.model.ExistingMemberResponseDto
import com.hocheol.respal.data.remote.model.JoinResponseDto
import com.hocheol.respal.data.remote.model.LoginResponseDto
import com.hocheol.respal.data.remote.model.NewMemberResponseDto
import com.hocheol.respal.data.remote.model.SampleResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RespalApi {
//    @GET("users/{owner}/repos")
//    fun getRepos(@Path("owner") owner: String) : Single<List<SampleResponse>>

    /** Oauth 정보를 서버로 보내 이미 회원인지 아닌지 판별 */
    @GET("oauth/user/{code}/?type=signup")
    fun sendOauthSignUp(@Path("code") code: String) : Single<NewMemberResponseDto>

    @GET("oauth/user/{code}/?type=callback")
    fun sendOauthCallBack(@Path("code") code: String) : Single<ExistingMemberResponseDto>

    /** Oauth 회원가입 */
    @POST("member/join")
    fun signUpOauth(@Body requestBody: RequestBody) : Single<JoinResponseDto>

    /** 로그인 */
    @POST("member/login")
    fun login() : Single<LoginResponseDto>

    /** 로그아웃 */
    @POST("member/logout")
//    @Header("Bearer")
    fun logout() : Single<List<SampleResponse>>

    /** TEST */
    @GET("test")
//    @Header("Bearer")
    fun test() : Single<List<SampleResponse>>

}