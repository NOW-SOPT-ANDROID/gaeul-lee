package com.sopt.now.data.datasourceImpl

import com.sopt.now.data.datasource.FriendDataSource
import com.sopt.now.data.dto.response.ResponseFriendsDto
import com.sopt.now.data.service.FriendService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendDataSourceImpl @Inject constructor(
    private val friendService: FriendService
) : FriendDataSource {
    override suspend fun getFriends(page: Int): Response<ResponseFriendsDto> {
        return friendService.getFriends(page)
    }
}