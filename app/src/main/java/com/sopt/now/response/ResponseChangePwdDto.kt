package com.sopt.now.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseChangePwdDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)
