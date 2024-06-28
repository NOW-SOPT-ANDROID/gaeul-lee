package com.sopt.now.domain

import com.sopt.now.data.remote.request.RequestChangePwdDto
import com.sopt.now.data.remote.response.ResponseChangePwdDto
import com.sopt.now.data.remote.response.ResponseFriendsDto
import com.sopt.now.data.remote.response.ResponseUserInfoDto
import com.sopt.now.data.remote.service.FriendService
import com.sopt.now.data.remote.service.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowerRepository @Inject constructor(
    private val friendService: FriendService,
    private val userService: UserService,
) {
    suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto> {
        return userService.getUserInfo(userId)
    }
    suspend fun changeUserPwd(userId: Int, request: RequestChangePwdDto) : Response<ResponseChangePwdDto> {
        return userService.changeUserPwd(userId, request)
    }
    suspend fun getFriends(page: Int): Response<ResponseFriendsDto> {
        return friendService.getFriends(page)
    }
}