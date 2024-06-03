package com.sopt.now.compose.feature.home

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.feature.main.MainViewModel
import com.sopt.now.compose.profileItem.FriendProfileItem
import com.sopt.now.compose.profileItem.UserProfileItem

@Composable
fun HomeScreen(context: Context, userId: Int) {
    val homeViewModel: HomeViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()

    val userInfo = mainViewModel.userInfo.observeAsState().value
    val friends = homeViewModel.friendList.observeAsState(emptyList()).value
    mainViewModel.fetchUserInfo(userId)
    homeViewModel.fetchFriends(2)

    LazyColumn {
        item {
            if (userInfo != null) {
                UserProfileItem(userInfo)
            }
        }
        items(friends.size) { friend -> FriendProfileItem(friend = friends[friend]) }
    }

}
