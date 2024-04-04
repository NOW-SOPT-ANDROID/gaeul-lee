package com.sopt.now.compose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private val users : MutableList<User> = mutableListOf()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val userInfo = result.data?.getSerializableExtra("userInfo") as? User
            userInfo?.let {
                users.add(it)
            }
            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onClickLoginBtn = {
                            id, pwd ->
                            val result = isLoginPossible(id, pwd)
                            if(result != null){
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("login", result)
                                startActivity(intent)
                            }
                        },
                        onClickSignUpBtn = {
                            val intent = Intent(this, SignUpActivity::class.java)
                            resultLauncher.launch(intent)
                        }
                    )
                }
            }
        }
    }


    fun isLoginPossible(id: String, pwd:String) : User? {
        var result : User? = null
        var message = ""
        users.forEach { user ->
            when {
                user.id == id && user.pwd == pwd -> {
                    result = user
                    message = "로그인에 성공했습니다."
                }
                user.id == id && user.pwd != pwd -> {
                    message = "비밀번호가 틀렸습니다."
                }
                user.id != id -> {
                    message = "존재하지 않는 아이디입니다."
                }
            }
        }
        if(message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        return result
    }
}

@Composable
fun LoginScreen(
    onClickLoginBtn : (String, String) -> Unit,
    onClickSignUpBtn : () -> Unit
){
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
                onClickLoginBtn(id, pwd)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ){
            Text("로그인하기")}
        Button(
            onClick = {
                onClickSignUpBtn()
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
        LoginScreen(onClickLoginBtn = {id, pwd ->},
            onClickSignUpBtn = {})
    }
}