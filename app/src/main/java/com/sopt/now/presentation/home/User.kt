package com.sopt.now.presentation.home

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val authenticationId: String,
    val nickname: String,
    val phone: String,
)