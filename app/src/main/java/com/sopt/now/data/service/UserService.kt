package com.sopt.now.data.service

import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserService {
    @GET("member/info")
    suspend fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Response<ResponseUserInfoDto>

    @PATCH("member/password")
    suspend fun changeUserPwd(
        @Header("memberId") userId: Int,
        @Body request: RequestChangePwdDto,
    ): Response<ResponseChangePwdDto>
}