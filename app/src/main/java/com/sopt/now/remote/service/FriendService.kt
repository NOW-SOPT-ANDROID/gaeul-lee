package com.sopt.now.remote.service

import com.sopt.now.remote.response.ResponseFriendsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendService {
    @GET("users")
    fun getFriends(
        @Query("page") page: Int,
    ): Call<ResponseFriendsDto>
}