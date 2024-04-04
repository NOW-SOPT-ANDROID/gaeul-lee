package com.sopt.now.compose

import java.io.Serializable

data class User(
    val userId: String,
    val userPassword: String,
    val userNickname: String,
    val userMBTI: String
) : Serializable
