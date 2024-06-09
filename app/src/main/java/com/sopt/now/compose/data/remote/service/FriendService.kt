package com.sopt.now.compose.data.remote.service

import com.sopt.now.compose.data.remote.response.ResponseFriendsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    suspend fun getFriends(
        @Query("page") page: Int,
    ): Response<ResponseFriendsDto>
}