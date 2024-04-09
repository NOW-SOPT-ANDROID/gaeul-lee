package com.sopt.now.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.AspectRatio
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
const val USER_INFO = "UserInfo"
const val LOGIN_INFO = "LoginInfo"
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
}

@Composable
fun MainScreen(user: User) {

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
                colorFilter = ColorFilter.tint(Color.Green),
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
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    NOWSOPTAndroidTheme {
        MainScreen(user = User("id", "pwd", "nickname", "mbti"))
    }
}}