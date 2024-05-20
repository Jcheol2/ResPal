package com.hocheol.respal.data.remote.api

import com.google.gson.JsonObject
import com.hocheol.respal.data.remote.model.*
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RespalApi {
    @GET("test")
    suspend fun test() : JsonObject

    /** 회원가입 */
    @POST("member/join")
    suspend fun signUp(
        @Body requestBody: RequestBody
    ) : SignUpResponseDto

    /** 회원가입시 이메일 인증 */
    @GET("member/email")
    suspend fun emailAuth(
        @Query("email") inputEmail: String
    ) : ResponseBody

    /** 로그인 */
    @POST("member/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ) : LoginResponseDto

//    /** 임시 비밀번호 이메일 전송 */
//    @POST("password")
//    fun sendTempPassword(@Body requestBody: RequestBody) : Single<ResponseBody>

    /** 계정찾기 */
    @POST("password")
    suspend fun findAccount(
        @Body requestBody: RequestBody
    ) : FindAccountResponseDto

    /** 비밀번호 재설정 */
    @PATCH("password")
    suspend fun changePassword(
        @Body requestBody: RequestBody
    ) : ResponseBody

    /** 로그아웃 */
    @POST("member/logout")
    suspend fun logout() : ResponseBody

    /** OAuth 로그인 */
    @GET("oauth/app/login/{provider}")
    suspend fun oauthLogin(
        @Path("provider") provider: String,
        @Query("code") code: String
    ) : ResponseBody

    /** Oauth 정보를 서버로 보내 이미 회원인지 아닌지 판별 */
    @GET("oauth/user/{uid}")
    suspend fun requestOauthInfo(
        @Path("uid") uid: String,
        @Query("type") type: String
    ) : ResponseBody

    /** 멘션시 필요한 사용자 검색 */
    @GET("members")
    suspend fun searchMembers(
        @Query("searchWord") searchWord: String,
        @Query("limit") limit: Int
    ) : ResponseBody

    /** 태그 제거*/
    @POST("tag/{tagId}")
    suspend fun deleteTag(
        @Path("tagId") tagId: String
    ) : ResponseBody

    /** 태그 생성 */
    @POST("tag/{resumeId}")
    suspend fun createTag(
        @Path("resumeId") resumeId: String
    ) : ResponseBody

    /** ME 이력서 조회 */
    @GET("resume?type=me")
     fun findMyResume() : ResponseBody

    /** HUB 이력서 조회 */
    @GET("resume?type=hub")
     fun findHubResume() : JsonObject

    /** TAG 이력서 조회 */
    @GET("resume?type=tag")
    suspend fun findTagResume() : JsonObject

    /** 이력서 생성 */
    @POST("resume")
    suspend fun createResume() : ResponseBody

    /** 이력서 파일 저장 */
    @POST("resume/file")
    suspend fun saveResumeFile() : ResponseBody

    /** 이력서 파일 삭제 */
    @HTTP(method = "DELETE", path = "resume/file", hasBody = true)
    suspend fun deleteResumeFile() : ResponseBody

    /** 이력서 상세 조회 */
    @GET("resume/{resumeId}")
    suspend fun getDetailResume() : ResponseBody

    /** 이력서 삭제 */
    @HTTP(method = "DELETE", path = "resume/{resumeId}", hasBody = true)
    suspend fun deleteResume() : ResponseBody

    /** 댓글 조회 */
    @GET("comment/{resumeId}")
    suspend fun getComment() : ResponseBody

    /** 댓글 생성 */
    @POST("comment/{resumeId}")
    suspend fun createComment() : ResponseBody

    /** 댓글 삭제 */
    @HTTP(method = "DELETE", path = "comment/{commentId}", hasBody = true)
    suspend fun deleteComment() : ResponseBody
}