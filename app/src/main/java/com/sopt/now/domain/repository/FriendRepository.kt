package com.sopt.now.domain.repository

import com.sopt.now.data.dto.response.ResponseFriendsDto
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface FriendRepository {
    suspend fun getFriends(page: Int): Response<ResponseFriendsDto>
}