package com.sopt.now.data.datasource

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignUpDto
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface AuthDataSource {
    suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
    suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto>
}