package com.sopt.now.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 화면 전환을 위한 navController 생성
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login_screen") {
                        // 로그인 화면 설정
                        composable(route = "login_screen") { navBackStackEntry ->
                            LoginScreen(
                                navController = navController,
                                user = navBackStackEntry.arguments?.get("user") as User?
                            )
                        }
                        // 회원가입 화면 설정
                        composable(route = "signup_screen") {
                            SignUpScreen(navController = navController)
                        }
                        composable(route = "main_screen") {
                            MainScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

fun isLoginPossible(context : Context, id: String, pwd:String, userId : String, userPassword:String) : Boolean{
    if(userId == "" || userPassword == "") {
        Toast.makeText(context, "정보를 입력해주세요", Toast.LENGTH_SHORT).show()
        return false
    }
    if(!id.equals(userId)){
        Toast.makeText(context, "아이디가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        return false
    }
    if(!pwd.equals(userPassword)){
        Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        return false
    }
    else return true
}

@Composable
fun LoginScreen(navController: NavController, user: User? = null){
    var id by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Welcome To SOPT",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text("ID")
        TextField(
            value = id,
            onValueChange = { id = it },
            placeholder = { Text("아이디를 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text("비밀번호")
        TextField(
            value = pwd,
            onValueChange = { pwd = it },
            placeholder = { Text("비밀번호를 입력해주세요") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            )
        )
        Spacer(modifier = Modifier.weight(2f))
        Button(
            onClick = {
                if (user != null) {
                    if(isLoginPossible(context, id, pwd, user.userId, user.userPassword)){
                        Toast.makeText(context, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        navController.navigate("main_screen")
                    } else{

                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ){
            Text("로그인하기")}
        Button(
            onClick = {
                navController.navigate("signup_screen")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ){
            Text("회원가입 하기")}
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginScreen(navController = rememberNavController())
    }
}