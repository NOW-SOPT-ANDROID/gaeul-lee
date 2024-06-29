package com.sopt.now.data.repositoryImpl

import com.sopt.now.data.datasource.UserDataSource
import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto> {
        return userDataSource.getUserInfo(userId)
    }

    override suspend fun changeUserPwd(
        userId: Int,
        request: RequestChangePwdDto
    ): Response<ResponseChangePwdDto> {
        return userDataSource.changeUserPwd(userId, request)
    }
}