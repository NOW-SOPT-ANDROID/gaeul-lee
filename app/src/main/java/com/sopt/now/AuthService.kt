package com.sopt.now

import com.sopt.now.request.RequestLoginDto
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseLoginDto
import com.sopt.now.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    fun signUp(
        @Body request: RequestSignUpDto,
    ): Call<ResponseSignUpDto>

    @POST("member/login")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>
}