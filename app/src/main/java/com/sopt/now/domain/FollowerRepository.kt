package com.sopt.now.domain

import com.sopt.now.data.remote.request.RequestChangePwdDto
import com.sopt.now.data.remote.response.ResponseChangePwdDto
import com.sopt.now.data.remote.response.ResponseFriendsDto
import com.sopt.now.data.remote.response.ResponseUserInfoDto
import retrofit2.Response

interface FollowerRepository {
    suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto>
    suspend fun changeUserPwd(userId: Int, request: RequestChangePwdDto) : Response<ResponseChangePwdDto>
    suspend fun getFriends(page: Int): Response<ResponseFriendsDto>
}