package com.sopt.now.data.service

import com.sopt.now.data.dto.response.ResponseFriendsDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    suspend fun getFriends(
        @Query("page") page: Int,
    ): Response<ResponseFriendsDto>
}