package com.sopt.now.data.datasourceImpl

import com.sopt.now.data.datasource.UserDataSource
import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.service.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto> {
        return userService.getUserInfo(userId)
    }

    override suspend fun changeUserPwd(
        userId: Int,
        pwd: RequestChangePwdDto
    ): Response<ResponseChangePwdDto> {
        return userService.changeUserPwd(userId, pwd)
    }
}