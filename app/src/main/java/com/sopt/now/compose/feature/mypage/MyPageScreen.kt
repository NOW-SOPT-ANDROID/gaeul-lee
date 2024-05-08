package com.sopt.now.compose.feature.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.feature.MainActivity.Companion.LOGIN_INFO
import com.sopt.now.compose.data.User
import com.sopt.now.compose.feature.MainActivity
import com.sopt.now.compose.feature.changePwd.ChangePwdActivity
import com.sopt.now.compose.feature.login.LoginActivity
import com.sopt.now.compose.remote.response.ResponseUserInfoDto
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun MyPageScreen(context: Context, userId: Int) {
    var userInfo by remember { mutableStateOf<User?>(null) }

    val mypageViewModel = ViewModelProvider(context as MainActivity).get(MyPageViewModel::class.java)

    LaunchedEffect(userId) {
        mypageViewModel.fetchUserInfo(context, userId,
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
                modifier = Modifier.size(150.dp),
                model = "https://avatars.githubusercontent.com/u/91470334?v=4",
                contentDescription = "User Image",
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
                mypageViewModel.onClickChangePwdBtn(context, userId)
            })
        RoundedCornerButton(
            buttonText = R.string.logout_btn_text,
            onClick = {
                mypageViewModel.onClickLogoutBtn(context)
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
