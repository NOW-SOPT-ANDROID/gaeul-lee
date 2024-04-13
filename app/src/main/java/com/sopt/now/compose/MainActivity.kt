package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.AspectRatio
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.fragment.MyPageFragment
import com.sopt.now.compose.profileItem.FriendProfileItem
import com.sopt.now.compose.profileItem.UserProfileItem
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

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
                    val userInfo = intent.getSerializableExtra("LOGIN_INFO") as? User
                    if(userInfo != null){
                        MainScreen(user = userInfo)
                }
            }
        }
    }

}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(user: User) {
    var context = LocalContext.current
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavigationItem(
            icon = Icons.Filled.Home,
            label = "홈"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Search,
            label = "검색"
        ),
        BottomNavigationItem(
            icon = Icons.Filled.Person,
            label = "마이페이지"
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
                        icon = {Icon(item.icon, contentDescription = item.label)},
                        label = {Text(item.label)},
                        selected = selectedItem == index,
                        onClick = {selectedItem = index}
                    )
                }
            }
        }
    ){
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding),
            ){

            when(selectedItem){
                0 -> {
                    LazyColumn {
                        items(friendList.size){
                            if (it == 0){
                                UserProfileItem(user = user)
                            } else FriendProfileItem(friend = friendList[it])
                        }
                    }
                }
                1 -> {
                    Text("검색")
                }
                2 -> {
                    MyPageFragment(context, user = user)
                }

            }
        }
    }
}
    val friendList = listOf<Friend>(
        Friend(
            profileImage = Icons.Filled.Face,
            name = "",
            selfDescription = ""
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지?",
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = Icons.Filled.Favorite,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지?",
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = Icons.Filled.Favorite,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "이의경",
            selfDescription = "다들 빨리 끝내고 뒤풀이 가고 싶지?",
        ),
        Friend(
            profileImage = Icons.Filled.Person,
            name = "우상욱",
            selfDescription = "나보다 안드 잘하는 사람 있으면 나와봐",
        ),
        Friend(
            profileImage = Icons.Filled.Favorite,
            name = "배지현",
            selfDescription = "표정 풀자 ^^",
        ),
    )
companion object{
    const val USER_INFO = "UserInfo"
    const val LOGIN_INFO = "LoginInfo"
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        MainScreen(user = User("id", "pwd", "nickname", "mbti"))
    }
}}