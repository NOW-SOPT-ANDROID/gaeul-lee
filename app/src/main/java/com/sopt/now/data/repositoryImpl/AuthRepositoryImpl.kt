package com.sopt.now.data.repositoryImpl

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.ResponseLoginDto
import com.sopt.now.data.dto.response.ResponseSignUpDto
import com.sopt.now.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signUp(request: RequestSignUpDto): Response<ResponseSignUpDto> {
        return authDataSource.signUp(request)
    }

    override suspend fun login(request: RequestLoginDto): Response<ResponseLoginDto> {
        return authDataSource.login(request)
    }
}