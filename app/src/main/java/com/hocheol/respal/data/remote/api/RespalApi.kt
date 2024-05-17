package com.hocheol.respal.data.remote.api

import com.google.gson.JsonObject
import com.hocheol.respal.data.remote.model.*
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RespalApi {
    @GET("test")
    fun test() : Single<JsonObject>

    /** 회원가입 */
    @POST("member/join")
    fun signUp(
        @Body requestBody: RequestBody
    ) : Single<SignUpResponseDto>

    /** 회원가입시 이메일 인증 */
    @GET("member/email")
    fun emailAuth(
        @Query("email") inputEmail: String
    ) : Single<ResponseBody>

    /** 로그인 */
    @POST("member/login")
    fun login(
        @Body requestBody: RequestBody
    ) : Single<LoginResponseDto>

//    /** 임시 비밀번호 이메일 전송 */
//    @POST("password")
//    fun sendTempPassword(@Body requestBody: RequestBody) : Single<ResponseBody>

    /** 계정찾기 */
    @POST("password")
    fun findAccount(
        @Body requestBody: RequestBody
    ) : Single<FindAccountResponseDto>

    /** 비밀번호 재설정 */
    @PATCH("password")
    fun changePassword(
        @Body requestBody: RequestBody
    ) : Single<ResponseBody>

    /** 로그아웃 */
    @POST("member/logout")
    fun logout() : Single<ResponseBody>

    /** OAuth 로그인 */
    @GET("oauth/app/login/{provider}")
    fun oauthLogin(
        @Path("provider") provider: String,
        @Query("code") code: String
    ) : Single<ResponseBody>

    /** Oauth 정보를 서버로 보내 이미 회원인지 아닌지 판별 */
    @GET("oauth/user/{uid}")
    fun requestOauthInfo(
        @Path("uid") uid: String,
        @Query("type") type: String
    ) : Single<ResponseBody>

    /** 멘션시 필요한 사용자 검색 */
    @GET("members")
    fun searchMembers(
        @Query("searchWord") searchWord: String,
        @Query("limit") limit: Int
    ) : Single<ResponseBody>

    /** 태그 제거*/
    @POST("tag/{tagId}")
    fun deleteTag(
        @Path("tagId") tagId: String
    ) : Single<ResponseBody>

    /** 태그 생성 */
    @POST("tag/{resumeId}")
    fun createTag(
        @Path("resumeId") resumeId: String
    ) : Single<ResponseBody>

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