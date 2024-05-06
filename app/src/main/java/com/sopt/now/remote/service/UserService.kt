package com.sopt.now.remote.service

import com.sopt.now.remote.request.RequestChangePwdDto
import com.sopt.now.remote.response.ResponseChangePwdDto
import com.sopt.now.remote.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserService {
    @GET("member/info")
    fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Call<ResponseUserInfoDto>

    @PATCH("member/password")
    fun changeUserPwd(
        @Header("memberId") userId: Int,
        @Body request: RequestChangePwdDto,
    ): Call<ResponseChangePwdDto>
}