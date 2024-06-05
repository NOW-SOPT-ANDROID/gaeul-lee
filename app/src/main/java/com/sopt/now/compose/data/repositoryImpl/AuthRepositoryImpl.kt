package com.sopt.now.compose.data.repositoryImpl

import com.sopt.now.compose.data.remote.request.RequestLoginDto
import com.sopt.now.compose.data.remote.request.RequestSignUpDto
import com.sopt.now.compose.data.remote.response.ResponseLoginDto
import com.sopt.now.compose.data.remote.response.ResponseSignUpDto
import com.sopt.now.compose.data.remote.service.AuthService
import com.sopt.now.compose.domain.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto> {
        return authService.signUp(request)
    }

    override suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto> {
        return authService.login(request)
    }
}