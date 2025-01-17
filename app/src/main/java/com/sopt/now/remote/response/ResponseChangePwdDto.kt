package com.sopt.now.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseChangePwdDto(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)
