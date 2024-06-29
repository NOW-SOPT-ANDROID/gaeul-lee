package com.sopt.now.data.datasource

import com.sopt.now.data.dto.response.ResponseFriendsDto
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface FriendDataSource {
    suspend fun getFriends(page: Int): Response<ResponseFriendsDto>
}