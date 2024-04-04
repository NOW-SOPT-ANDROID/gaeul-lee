package com.sopt.now.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SignUpScreen(navController = navController)
                }
            }
        }
    }
}
@Composable
fun SignUpScreen(navController: NavController){
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userNickname by remember { mutableStateOf("") }
    var userMBTI by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "SIGN UP",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Text("ID")
        TextField(
            value = userId,
            onValueChange = { userId = it },
            placeholder = { Text("아이디를 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text("비밀번호")
        TextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            placeholder = { Text("비밀번호를 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text("닉네임")
        TextField(
            value = userNickname,
            onValueChange = { userNickname = it },
            placeholder = { Text("닉네임을 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text("MBTI")
        TextField(
            value = userMBTI,
            onValueChange = { userMBTI = it },
            placeholder = { Text("MBTI를 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(2f))

        Button(
            onClick = {
                if(isSignUpPossible(context, userId, userPassword, userNickname, userMBTI)){
                    val userInfo = User(userId, userPassword, userNickname, userMBTI)
                    val bundle = bundleOf("userInfo" to userInfo)
                    Toast.makeText(context, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    navController.navigate("login_screen/${bundle}"){
                        launchSingleTop = true
                        popUpTo("login_screen"){
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp)
        ){
            Text("회원가입 하기")
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

fun isSignUpPossible(context: Context, userId: String, userPassword: String, userNickname: String, userMBTI: String): Boolean {
    if(userId.length !in 6..10) {
        Toast.makeText(context, "아이디는 6자 이상 10자 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if(userPassword.length !in 8..12){
        Toast.makeText(context, "비밀번호는 8자 이상 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if(userNickname.isBlank() || userNickname.contains(" ")) {
        Toast.makeText(context, "닉네임은 공백 없이 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if(userMBTI.isBlank()) {
        Toast.makeText(context, "MBTI를 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    NOWSOPTAndroidTheme {
        SignUpScreen(navController = rememberNavController())
    }
}