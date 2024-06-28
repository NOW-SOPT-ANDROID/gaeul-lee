package com.sopt.now.data.repositoryImpl

import com.sopt.now.data.remote.request.RequestChangePwdDto
import com.sopt.now.data.remote.response.ResponseChangePwdDto
import com.sopt.now.data.remote.response.ResponseFriendsDto
import com.sopt.now.data.remote.response.ResponseUserInfoDto
import com.sopt.now.data.remote.service.FriendService
import com.sopt.now.data.remote.service.UserService
import com.sopt.now.domain.FollowerRepository
import retrofit2.Response

//class FollowerRepositoryImpl(
//    private val userService: UserService,
//    private val friendService: FriendService
//) : FollowerRepository {
//    override suspend fun getUserInfo(userId: Int): Response<ResponseUserInfoDto> {
//        return userService.getUserInfo(userId)
//    }
//
//    override suspend fun changeUserPwd(
//        userId: Int,
//        request: RequestChangePwdDto
//    ): Response<ResponseChangePwdDto> {
//        return userService.changeUserPwd(userId, request)
//    }
//
//    override suspend fun getFriends(page: Int): Response<ResponseFriendsDto> {
//        return friendService.getFriends(page)
//    }
//}