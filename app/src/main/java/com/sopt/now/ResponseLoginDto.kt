package com.sopt.now

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResponseLoginDto (
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)
