package com.sopt.now.domain

import com.sopt.now.data.remote.request.RequestLoginDto
import com.sopt.now.data.remote.request.RequestSignUpDto
import com.sopt.now.data.remote.response.ResponseLoginDto
import com.sopt.now.data.remote.response.ResponseSignUpDto
import com.sopt.now.data.remote.service.AuthService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService
){
    suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto> {
        return authService.signUp(request)
    }
    suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto> {
        return authService.login(request)
    }
}