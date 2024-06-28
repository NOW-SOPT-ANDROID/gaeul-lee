package com.sopt.now.domain.repository

import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignUpDto
import com.sopt.now.data.service.AuthService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface AuthRepository{
    suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto>
    suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto>
}