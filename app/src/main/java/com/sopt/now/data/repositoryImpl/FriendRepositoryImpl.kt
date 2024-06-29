package com.sopt.now.data.repositoryImpl

import com.sopt.now.data.datasource.FriendDataSource
import com.sopt.now.data.dto.response.ResponseFriendsDto
import com.sopt.now.domain.repository.FriendRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRepositoryImpl @Inject constructor(
    private val friendDataSource: FriendDataSource
) : FriendRepository {
    override suspend fun getFriends(page: Int): Response<ResponseFriendsDto> {
        return friendDataSource.getFriends(page)
    }
}