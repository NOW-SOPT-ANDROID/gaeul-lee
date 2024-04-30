package com.sopt.now

import com.sopt.now.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("member/info")
    fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Call<ResponseUserInfoDto>
}