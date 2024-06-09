package com.sopt.now.compose.presentation.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sopt.now.compose.R
import com.sopt.now.compose.data.remote.ServicePool.friendService
import com.sopt.now.compose.data.remote.ServicePool.userService
import com.sopt.now.compose.data.repositoryImpl.FollowerRepositoryImpl
import com.sopt.now.compose.domain.FollowerRepository
import com.sopt.now.compose.presentation.changePwd.ChangePwdActivity
import com.sopt.now.compose.presentation.login.LoginActivity
import com.sopt.now.compose.presentation.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.compose.util.BaseViewModelFactory
import com.sopt.now.compose.util.NOWSOPTAndroidTheme
import com.sopt.now.compose.util.PreferencesUtil
import com.sopt.now.compose.util.RoundedCornerButton

@Composable
fun MyPageScreen(context: Context, userId: Int) {
    val followerRepository: FollowerRepository by lazy {
        FollowerRepositoryImpl(
            userService,
            friendService
        )
    }
    val viewModel: MyPageViewModel =
        viewModel(factory = BaseViewModelFactory { MyPageViewModel(followerRepository) })
    val userInfo = viewModel.userInfo.observeAsState().value
    viewModel.fetchUserInfo(userId)
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
                modifier = Modifier
                    .size(200.dp)
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
                val intent = Intent(context, ChangePwdActivity::class.java)
                val bundle = Bundle().apply {
                    putInt(LOGIN_INFO, userId)
                }
                intent.putExtras(bundle)
                context.startActivity(intent)
            })
        RoundedCornerButton(
            buttonText = R.string.logout_btn_text,
            onClick = {
                PreferencesUtil.clearUserId(context)
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
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