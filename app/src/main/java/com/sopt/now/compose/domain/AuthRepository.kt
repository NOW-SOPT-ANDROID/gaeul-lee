package com.sopt.now.compose.domain

import com.sopt.now.compose.data.remote.request.RequestLoginDto
import com.sopt.now.compose.data.remote.request.RequestSignUpDto
import com.sopt.now.compose.data.remote.response.ResponseLoginDto
import com.sopt.now.compose.data.remote.response.ResponseSignUpDto
import retrofit2.Response

interface AuthRepository {
    suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
    suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto>
}