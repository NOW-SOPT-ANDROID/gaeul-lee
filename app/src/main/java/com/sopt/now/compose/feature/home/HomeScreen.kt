package com.sopt.now.compose.feature.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.compose.data.Friend
import com.sopt.now.compose.data.User
import com.sopt.now.compose.feature.main.MainActivity
import com.sopt.now.compose.feature.main.MainViewModel
import com.sopt.now.compose.profileItem.FriendProfileItem
import com.sopt.now.compose.profileItem.UserProfileItem

@Composable
fun HomeScreen(context: Context, userId: Int) {
    var userInfo by remember { mutableStateOf<User?>(null) }
    var friendList by remember { mutableStateOf<List<Friend>>(listOf()) }
    val homeViewModel = ViewModelProvider(context as MainActivity)[HomeViewModel::class.java]
    val mainViewModel = ViewModelProvider(context)[MainViewModel::class.java]

    LaunchedEffect(userId) {
        mainViewModel.fetchUserInfo(userId,
            onSuccess = { user -> userInfo = user },
            onFailure = { error -> Log.e("HomeScreen", "Error: $error") }
        )
        homeViewModel.fetchFriendsInfo(
            onSuccess = { friends -> friendList = friends },
            onFailure = { error -> Log.e("HomeScreen", "Error: $error") }
        )
    }

    LazyColumn {
        item {
            userInfo?.let { UserProfileItem(user = it) }
        }
        items(friendList.size) { friend ->
            FriendProfileItem(friend = friendList[friend])
        }
    }

}
