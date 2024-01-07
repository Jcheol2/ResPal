package com.hocheol.respal.data.local.model

data class UserInfo(
    val email: String,
    val password: String?,
    val picture: String,
    val nickname: String,
    val provider: String
)