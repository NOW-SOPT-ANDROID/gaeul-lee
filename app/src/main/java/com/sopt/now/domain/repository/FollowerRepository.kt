package com.sopt.now.domain.repository

import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseFriendsDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.service.FriendService
import com.sopt.now.data.service.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface FollowerRepository {
    suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto>
    suspend fun changeUserPwd(userId: Int, request: RequestChangePwdDto) : Response<ResponseChangePwdDto>
    suspend fun getFriends(page: Int): Response<ResponseFriendsDto>
}