package com.sopt.now.data.repositoryImpl

import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.data.dto.response.ResponseChangePwdDto
import com.sopt.now.data.dto.response.ResponseFriendsDto
import com.sopt.now.data.dto.response.ResponseUserInfoDto
import com.sopt.now.data.service.FriendService
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.repository.FollowerRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowerRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val friendService: FriendService
) : FollowerRepository {
    override suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto> {
        return userService.getUserInfo(userId)
    }

    override suspend fun changeUserPwd(
        userId: Int,
        request: RequestChangePwdDto
    ): Response<ResponseChangePwdDto> {
        return userService.changeUserPwd(userId, request)
    }

    override suspend fun getFriends(page: Int): Response<ResponseFriendsDto> {
        return friendService.getFriends(page)
    }
}