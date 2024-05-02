package com.sopt.now.compose.service

import com.sopt.now.compose.response.ResponseFriendsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    fun getFriends(
        @Query("page") page: Int,
    ): Call<ResponseFriendsDto>
}