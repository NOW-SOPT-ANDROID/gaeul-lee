package com.sopt.now.compose.presentation.home

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.data.remote.ServicePool.friendService
import com.sopt.now.compose.data.remote.ServicePool.userService
import com.sopt.now.compose.data.repositoryImpl.FollowerRepositoryImpl
import com.sopt.now.compose.domain.FollowerRepository
import com.sopt.now.compose.util.BaseViewModelFactory

@Composable
fun HomeScreen(context: Context, userId: Int) {
    val followerRepository: FollowerRepository by lazy {
        FollowerRepositoryImpl(
            userService,
            friendService
        )
    }
    val viewModel: HomeViewModel =
        viewModel(factory = BaseViewModelFactory { HomeViewModel(followerRepository) })
    val userInfo = viewModel.userInfo.observeAsState().value
    val friends = viewModel.friendList.observeAsState(emptyList()).value
    viewModel.fetchUserInfo(userId)
    viewModel.fetchFriends(PAGE)

    LazyColumn {
        item {
            if (userInfo != null) {
                UserProfileItem(userInfo)
            }
        }
        items(friends.size) { friend -> FriendProfileItem(friend = friends[friend]) }
    }

}

const val PAGE = 2