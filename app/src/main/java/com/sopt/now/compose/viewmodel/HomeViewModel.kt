package com.sopt.now.compose.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.Friend

class HomeViewModel : ViewModel() {
    val mockFriendList = listOf<Friend>(
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
}