package com.hocheol.respal.data.remote.model

import com.google.gson.annotations.SerializedName

data class SampleResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("html_url")
    val url: String
)

data class NewMemberResponseDto(
    val statusCode: Int,
    val result: NewMemberResultDto
)

data class NewMemberResultDto(
    val userInfo: UserInfoDto,
    val provider: String
)

data class ExistingMemberResponseDto(
    val statusCode: Int,
    val result: ExistingMemberResultDto
)

data class ExistingMemberResultDto(
    val userInfo: UserInfoDto,
    val provider: String,
    val accessToken: String,
    val refreshToken: String
)

data class UserInfoDto(
    val userInfoId: String,
    val email: String,
    val image: String,
    val nickname: String
)

data class JoinResponseDto(
    val statusCode: Int,
    val result: JoinResponseResultDto
)

data class JoinResponseResultDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val membersEmail: String,
    val tmpPasswordStatus: String?
)

data class LoginResponseDto(
    val statusCode: Int,
    val data: LoginResponseDataDto

)
data class LoginResponseDataDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val membersEmail: String,
    val tmpPasswordStatus: String // tmpPasswordStatus 값을 확인해서 Y인 경우 , 비밀번호 재설정 폼으로 이동
)