package com.sopt.now.compose.fragment

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import com.sopt.now.compose.LoginActivity
import com.sopt.now.compose.R
import com.sopt.now.compose.User
import com.sopt.now.compose.ui.theme.RoundedCornerButton

@Composable
fun MyPageFragment(context: Context, user: User){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Image(modifier = Modifier
                .size(100.dp)
                .aspectRatio(1f),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "User Image",
                colorFilter = ColorFilter.tint(colorResource(id = R.color.pink)),
                contentScale = ContentScale.Fit)

            Text(text = user.nickname,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically))
        }
        Text(text = stringResource(id = R.string.description),
            fontSize = 20.sp)

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            stringResource(id = R.string.id_text),
            fontSize = 30.sp)
        Text(text = user.id,
            fontSize = 20.sp,
            color = Color.Gray)
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = R.string.pw_text),
            fontSize = 30.sp)
        Text(text = user.pwd,
            fontSize = 20.sp,
            color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        RoundedCornerButton(
            buttonText = R.string.logout_btn_text,
            onClick = {
                onClickLogoutBtn(context, user)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

fun onClickLogoutBtn(context : Context, user : User) {
    val intent = Intent(context, LoginActivity::class.java)
    startActivity(context, intent, bundleOf("user" to user))
}
