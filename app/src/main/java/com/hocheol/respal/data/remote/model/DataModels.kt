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

data class LoginResponseDTO(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val membersEmail: String,
    val tmpPasswordStatus: String // tmpPasswordStatus 값을 확인해서 Y인 경우 , 비밀번호 재설정 폼으로 이동
)

data class JoinResponseDTO(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val membersEmail: String
)

data class ResponseDTO(
    val shopId: String,
//    val type: String,
//    val date: String,
    val result: String
)