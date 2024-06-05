package com.sopt.now.domain

import com.sopt.now.data.remote.request.RequestLoginDto
import com.sopt.now.data.remote.request.RequestSignUpDto
import com.sopt.now.data.remote.response.ResponseLoginDto
import com.sopt.now.data.remote.response.ResponseSignUpDto
import retrofit2.Response

interface AuthRepository {
    suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
    suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto>
}