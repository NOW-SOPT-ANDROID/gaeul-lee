package com.sopt.now.compose.remote.service

import com.sopt.now.compose.remote.request.RequestLoginDto
import com.sopt.now.compose.remote.request.RequestSignUpDto
import com.sopt.now.compose.remote.response.ResponseLoginDto
import com.sopt.now.compose.remote.response.ResponseSignUpDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    suspend fun signUp(
        @Body request: RequestSignUpDto,
    ): Response<ResponseSignUpDto>

    @POST("member/login")
    suspend fun login(
        @Body request: RequestLoginDto,
    ): Response<ResponseLoginDto>
}