package com.sopt.now.util.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val authenticationId: String,
    val nickname: String,
    val phone: String,
)