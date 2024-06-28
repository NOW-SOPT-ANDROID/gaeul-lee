package com.sopt.now.data.repositoryImpl

import com.sopt.now.domain.AuthRepository
import com.sopt.now.data.remote.request.RequestLoginDto
import com.sopt.now.data.remote.request.RequestSignUpDto
import com.sopt.now.data.remote.response.ResponseLoginDto
import com.sopt.now.data.remote.response.ResponseSignUpDto
import com.sopt.now.data.remote.service.AuthService
import retrofit2.Response
//
//class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
//    override suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto> {
//        return authService.signUp(request)
//    }
//
//    override suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto> {
//        return authService.login(request)
//    }
//}