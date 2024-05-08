package com.sopt.now.compose.feature.mypage

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.sopt.now.compose.R
import com.sopt.now.compose.data.User
import com.sopt.now.compose.feature.main.MainActivity
import com.sopt.now.compose.feature.main.MainViewModel
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton

@Composable
fun MyPageScreen(context: Context, userId: Int) {
    var userInfo by remember { mutableStateOf<User?>(null) }

    val myPageViewModel =
        ViewModelProvider(context as MainActivity)[MyPageViewModel::class.java]
    val mainViewModel = ViewModelProvider(context)[MainViewModel::class.java]

    LaunchedEffect(userId) {
        mainViewModel.fetchUserInfo(userId,
            onSuccess = { user -> userInfo = user },
            onFailure = { error -> Log.e("MyPageScreen", "Error: $error") }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.size(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                model = "https://avatars.githubusercontent.com/u/91470334?v=4",
                contentDescription = "User Image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = userInfo?.nickname ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.description),
                fontSize = 20.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(id = R.string.id_text),
            fontSize = 25.sp
        )
        Text(
            text = userInfo?.authenticationId ?: "",
            fontSize = 20.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(id = R.string.phone_text),
            fontSize = 25.sp
        )
        Text(
            text = userInfo?.phone ?: "",
            fontSize = 20.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        RoundedCornerButton(
            buttonText = R.string.change_pwd_btn_text,
            onClick = {
                myPageViewModel.onClickChangePwdBtn(context, userId)
            })
        RoundedCornerButton(
            buttonText = R.string.logout_btn_text,
            onClick = {
                myPageViewModel.onClickLogoutBtn(context)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    NOWSOPTAndroidTheme {
        MyPageScreen(LocalContext.current, 0)
    }
}
