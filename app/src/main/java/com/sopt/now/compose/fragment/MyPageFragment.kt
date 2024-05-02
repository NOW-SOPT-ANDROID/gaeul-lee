package com.sopt.now.compose.fragment

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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import coil.compose.AsyncImage
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.activity.ChangePwdActivity
import com.sopt.now.compose.activity.LoginActivity
import com.sopt.now.compose.activity.MainActivity.Companion.LOGIN_INFO
import com.sopt.now.compose.data.User
import com.sopt.now.compose.response.ResponseUserInfoDto
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
fun MyPageFragment(context: Context, userId: Int) {
    var userInfo by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(userId) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userInfo = getUserInfo(userId)
            } catch (e: Exception) {
                Log.e("MyPageFragment", "Error: ${e.message}")
            }
        }
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
                onClickChangePwdBtn(context, userId)
            })
        RoundedCornerButton(
            buttonText = R.string.logout_btn_text,
            onClick = {
                onClickLogoutBtn(context)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

suspend fun getUserInfo(userId: Int): User {
    return suspendCoroutine { continuation ->
        ServicePool.userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseUserInfoDto? = response.body()
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
                    continuation.resumeWithException(Exception("Failed to fetch user info"))
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}
fun onClickChangePwdBtn(context: Context, userId: Int) {
    val intent = Intent(context, ChangePwdActivity::class.java)
    val bundle = Bundle().apply {
        putInt(LOGIN_INFO, userId)
    }
    intent.putExtras(bundle)
    startActivity(context, intent, null)
}

fun onClickLogoutBtn(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    startActivity(context, intent, null)

}
