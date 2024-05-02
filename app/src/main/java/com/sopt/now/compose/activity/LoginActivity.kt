package com.sopt.now.compose.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.activity.MainActivity.Companion.LOGIN_INFO
import com.sopt.now.compose.request.RequestLoginDto
import com.sopt.now.compose.response.ResponseLoginDto
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    LoginScreen(
                        onClickLoginBtn = { id, pwd ->
                            val loginRequest = getLoginRequestDto(id, pwd)
                            ServicePool.authService.login(loginRequest).enqueue(object :
                                Callback<ResponseLoginDto> {
                                override fun onResponse(
                                    call: Call<ResponseLoginDto>,
                                    response: Response<ResponseLoginDto>
                                ) {
                                    if (response.isSuccessful) {
                                        val userId = response.headers()["location"]
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "로그인 성공 유저의 ID는 $userId 입니둥",
                                            Toast.LENGTH_SHORT,
                                        ).show()

                                        val intent =
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra(LOGIN_INFO, userId)
                                        startActivity(intent)
                                    } else {
                                        val error = response.message()
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "로그인이 실패 $error",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                }


                                override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        t.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })
                        },
                        onClickSignUpBtn = {
                            val intent = Intent(this, SignUpActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun getLoginRequestDto(id: String, pwd: String): RequestLoginDto {
        return RequestLoginDto(
            authenticationId = id,
            password = pwd,
        )

    }
}

@Composable
fun LoginScreen(
    onClickLoginBtn: (String, String) -> Unit,
    onClickSignUpBtn: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.welcome_text),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        LabeledTextField(
            labelTextId = R.string.id_text,
            value = id,
            onValueChange = { id = it },
            placeholderTextId = R.string.id_hint
        )
        Spacer(modifier = Modifier.height(30.dp))

        LabeledTextField(
            labelTextId = R.string.pw_text,
            value = pwd,
            onValueChange = { pwd = it },
            placeholderTextId = R.string.pw_hint,
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.weight(2f))
        RoundedCornerButton(
            buttonText = R.string.login_btn_text,
            onClick = {
                onClickLoginBtn(id, pwd)
            }
        )
        RoundedCornerButton(
            buttonText = R.string.signup_btn_text,
            onClick = {
                onClickSignUpBtn()
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginScreen(onClickLoginBtn = { id, pwd -> },
            onClickSignUpBtn = {})
    }
}