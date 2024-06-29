package com.sopt.now.data.datasource

import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface UserDataSource {
    suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto>
    suspend fun changeUserPwd(userId: Int, pwd: RequestChangePwdDto): Response<ResponseChangePwdDto>
}