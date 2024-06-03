package com.sopt.now.ui.login

data class LoginState(
    val isSuccess: Boolean,
    val message: String,
    val userId: String? = null,
)
