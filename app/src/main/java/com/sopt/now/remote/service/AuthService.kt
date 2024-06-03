package com.sopt.now.remote.service

import com.sopt.now.remote.request.RequestLoginDto
import com.sopt.now.remote.request.RequestSignUpDto
import com.sopt.now.remote.response.ResponseLoginDto
import com.sopt.now.remote.response.ResponseSignUpDto
import retrofit2.Call
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