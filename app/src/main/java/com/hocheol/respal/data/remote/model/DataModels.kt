package com.hocheol.respal.data.remote.model

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

data class SignUpResponseDto(
    val statusCode: Int,
    val result: SignUpResultDto
)

data class SignUpResultDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val membersEmail: String,
    val tmpPasswordStatus: String?
)

data class LoginResponseDto(
    val statusCode: Int,
    val result: LoginResultDto
)

data class LoginResultDto(
//    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
//    val membersEmail: String,
//    val tmpPasswordStatus: String // tmpPasswordStatus 값을 확인해서 Y인 경우 , 비밀번호 재설정 폼으로 이동
)

data class LogoutResponseDto(
    val statusCode: Int,
    val result: String
)

data class FindAccountResponseDto(
    val statusCode: Int,
    val result: String
)

data class RefreshTokenResponseDto(
    val statusCode: Int,
    val result: RefreshTokenResultDto

)
data class RefreshTokenResultDto(
    val message: String,
    val accessToken: String
)