package com.hocheol.respal.data.remote.api

import com.google.gson.JsonObject
import com.hocheol.respal.data.remote.model.ExistingMemberResponseDto
import com.hocheol.respal.data.remote.model.SignUpResponseDto
import com.hocheol.respal.data.remote.model.LoginResponseDto
import com.hocheol.respal.data.remote.model.NewMemberResponseDto
import com.hocheol.respal.data.remote.model.RefreshTokenResponseDto
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RespalApi {
    @GET("test")
    fun test() : Single<JsonObject>

    /** 임시 비밀번호 이메일 전송 */
    @POST("password")
    fun sendTempPassword(@Body requestBody: RequestBody) : Single<ResponseBody>

    /** 비밀번호 재설정 */
    @PATCH("password")
    fun resetPassword(@Body requestBody: RequestBody) : Single<ResponseBody>

    /** 로그아웃 */
    @POST("member/logout")
    fun logout() : Single<JsonObject>

    /** 로그인 */
    @POST("member/login")
    fun login() : Single<LoginResponseDto>

    /** 회원가입 */
    @POST("member/join")
    fun signUp(@Body requestBody: RequestBody) : Single<SignUpResponseDto>

    /** Access Token 재발급 */
    @POST("jwt/refresh")
    fun reAuth(@Header("Authorization") refreshToken: String): Single<RefreshTokenResponseDto>

    /** OAuth 로그인 */
    @GET("oauth/app/login/{provider}")
    fun loginOauth(@Path("provider") provider: String) : Single<ResponseBody>

    /** OAuth 정보 요청 */
    @GET("oauth/user/{uid}")
    fun requestOauthInfo(@Path("uid") uid: String) : Single<ResponseBody>

    /** 멘션시 필요한 사용자 검색 */
    @GET("members")
    fun searchMembers() : Single<ResponseBody>

    /** 회원가입시 이메일 인증 */
    @GET("members/email")
    fun sendEmailAuth() : Single<ResponseBody>

    /** Oauth 정보를 서버로 보내 이미 회원인지 아닌지 판별 */
    @GET("oauth/user/{code}/?type=signup")
    fun sendOauthSignUp(@Path("code") code: String) : Single<NewMemberResponseDto>
    @GET("oauth/user/{code}/?type=callback")
    fun sendOauthCallBack(@Path("code") code: String) : Single<ExistingMemberResponseDto>

    /** 태그 제거*/
    @POST("tag/{tagId}")
    fun deleteTag(@Path("tagId") tagId: String) : Single<ResponseBody>

    /** 태그 생성 */
    @POST("tag/{resumeId}")
    fun createTag(@Path("resumeId") resumeId: String) : Single<ResponseBody>

    /** ME 이력서 조회 */
    @GET("resume?type=me")
    fun findMyResume() : Single<JsonObject>

    /** HUB 이력서 조회 */
    @GET("resume?type=hub")
    fun findHubResume() : Single<JsonObject>

    /** TAG 이력서 조회 */
    @GET("resume?type=tag")
    fun findTagResume() : Single<JsonObject>

    /** 이력서 생성 */
    @POST("resume")
    fun createResume() : Single<ResponseBody>

    /** 이력서 파일 저장 */
    @POST("resume/file")
    fun saveResumeFile() : Single<ResponseBody>

    /** 이력서 파일 삭제 */
    @HTTP(method = "DELETE", path = "resume/file", hasBody = true)
    fun deleteResumeFile() : Single<ResponseBody>

    /** 이력서 상세 조회 */
    @GET("resume/{resumeId}")
    fun getDetailResume() : Single<ResponseBody>

    /** 이력서 삭제 */
    @HTTP(method = "DELETE", path = "resume/{resumeId}", hasBody = true)
    fun deleteResume() : Single<ResponseBody>

    /** 댓글 조회 */
    @GET("comment/{resumeId}")
    fun getComment() : Single<ResponseBody>

    /** 댓글 생성 */
    @POST("comment/{resumeId}")
    fun createComment() : Single<ResponseBody>

    /** 댓글 삭제 */
    @HTTP(method = "DELETE", path = "comment/{commentId}", hasBody = true)
    fun deleteComment() : Single<ResponseBody>
}