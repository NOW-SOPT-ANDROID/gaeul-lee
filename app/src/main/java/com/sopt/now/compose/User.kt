package com.sopt.now.compose

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val authenticationId: String,
    val nickname: String,
    val phone: String
)
