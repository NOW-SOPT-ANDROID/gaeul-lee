package com.sopt.now.compose.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.now.compose.BottomNavigationItem
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.Friend
import com.sopt.now.compose.data.User
import com.sopt.now.compose.fragment.MyPageFragment
import com.sopt.now.compose.profileItem.FriendProfileItem
import com.sopt.now.compose.profileItem.UserProfileItem
import com.sopt.now.compose.response.ResponseFriendsDto
import com.sopt.now.compose.response.ResponseUserInfoDto
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userId = intent.getStringExtra(LOGIN_INFO)
                    Log.e("MainActivity", "userId: $userId")
                    if (userId != null) {
                        MainScreen(userId = userId.toInt())
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(userId: Int) {
        var userInfo by remember { mutableStateOf<User?>(null) }
        var mockFriendList by remember { mutableStateOf<List<Friend>>(listOf()) }

        LaunchedEffect(userId) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    userInfo = getUserInfo(userId)
                    mockFriendList = getFriendsInfo()
                } catch (e: Exception) {
                    Log.e("MyPageFragment", "Error: ${e.message}")
                }
            }
        }

        val context = LocalContext.current
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf(
            BottomNavigationItem(
                icon = Icons.Filled.Home,
                label = stringResource(id = R.string.bnv_home)
            ),
            BottomNavigationItem(
                icon = Icons.Filled.Search,
                label = stringResource(id = R.string.bnv_search)
            ),
            BottomNavigationItem(
                icon = Icons.Filled.Person,
                label = stringResource(id = R.string.bnv_mypage)
            )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(id = R.string.app_name))
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {

                when (selectedItem) {
                    0 -> {
                        LazyColumn {
                            item {
                                userInfo?.let { UserProfileItem(user = it) }
                            }
                            items(mockFriendList.size) { friend ->
                                FriendProfileItem(friend = mockFriendList[friend])
                            }
                        }
                    }

                    1 -> {
                        Text("검색")
                    }

                    2 -> {
                        MyPageFragment(context, userId = userId)
                    }

                }
            }
        }
    }

    suspend fun getFriendsInfo(): List<Friend> {
        var friendList = mutableListOf<Friend>()
        return suspendCoroutine { continuation ->
            ServicePool.friendService.getFriends(2).enqueue(object : Callback<ResponseFriendsDto> {

                override fun onResponse(
                    call: Call<ResponseFriendsDto>,
                    response: Response<ResponseFriendsDto>
                ) {
                    if (response.isSuccessful) {
                        val friends = response.body()?.data
                        Log.d("HomeViewModel", "friends: $friends")
                        friends?.forEach { friend ->
                            friendList.add(Friend(friend.avatar, friend.firstName, friend.email))
                        }
                        continuation.resume(friendList)
                    } else {
                        continuation.resumeWithException(Exception("Failed to fetch friends"))
                    }
                }

                override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                    Log.e("HomeViewModel", "onFailure: ${t.message}")
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getUserInfo(userId: Int): User {
        return suspendCoroutine { continuation ->
            ServicePool.userService.getUserInfo(userId)
                .enqueue(object : Callback<ResponseUserInfoDto> {
                    override fun onResponse(
                        call: Call<ResponseUserInfoDto>,
                        response: Response<ResponseUserInfoDto>,
                    ) {
                        if (response.isSuccessful) {
                            val data: ResponseUserInfoDto? = response.body()
                            Log.d("login", "data: $data, userId: $userId")
                            data?.let {
                                continuation.resume(
                                    User(
                                        it.data.authenticationId,
                                        it.data.nickname,
                                        it.data.phone
                                    )
                                )
                            }

                        } else {
                            val error = response.errorBody()
                            Log.e("HomeFragment", "error: $error")
                            continuation.resumeWithException(Exception("Failed to fetch user info"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                        Log.e("HomeFragment", "onFailure: ${t.message}")
                        continuation.resumeWithException(t)
                    }

                })
        }

    }

    companion object {
        const val USER_INFO = "UserInfo"
        const val LOGIN_INFO = "LoginInfo"
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPreview() {
        NOWSOPTAndroidTheme {
            MainScreen(userId = 0)
        }
    }
}