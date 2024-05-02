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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool.authService
import com.sopt.now.compose.activity.MainActivity.Companion.USER_INFO
import com.sopt.now.compose.request.RequestSignUpDto
import com.sopt.now.compose.response.ResponseSignUpDto
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    SignUpScreen(onClickSignUpBtn = { userId, userPassword, userNickname, userPhone ->
                        val signUpRequest =
                            getSignUpRequestDto(userId, userPassword, userNickname, userPhone)
                        authService.signUp(signUpRequest).enqueue(object :
                            Callback<ResponseSignUpDto> {
                            override fun onResponse(
                                call: Call<ResponseSignUpDto>,
                                response: Response<ResponseSignUpDto>
                            ) {
                                if (response.isSuccessful) {
                                    val userId = response.headers()["location"]
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "회원가입 성공 유저의 ID는 $userId 입니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@SignUpActivity, LoginActivity::class.java)
                                    intent.putExtra(USER_INFO, userId)
                                    finish()
                                } else {
                                    val error = response.message()
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "회원가입 실패 $error",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "서버 통신 실패 ${t.message}",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                        })
                    })
                }
            }
        }
    }

    private fun getSignUpRequestDto(
        userId: String,
        userPassword: String,
        userNickname: String,
        userPhone: String
    ): RequestSignUpDto {
        return RequestSignUpDto(
            authenticationId = userId,
            password = userPassword,
            nickname = userNickname,
            phone = userPhone,
        )
    }

    @Composable
    fun SignUpScreen(
        onClickSignUpBtn: (String, String, String, String) -> Unit
    ) {
        var userId by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var userNickname by remember { mutableStateOf("") }
        var userPhone by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.signup_text),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            LabeledTextField(
                labelTextId = R.string.id_text,
                value = userId,
                onValueChange = { userId = it },
                placeholderTextId = R.string.id_hint
            )

            Spacer(modifier = Modifier.height(30.dp))

            LabeledTextField(
                labelTextId = R.string.pw_text,
                value = userPassword,
                onValueChange = { userPassword = it },
                placeholderTextId = R.string.pw_hint,
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(30.dp))

            LabeledTextField(
                labelTextId = R.string.nickname_text,
                value = userNickname,
                onValueChange = { userNickname = it },
                placeholderTextId = R.string.nickname_hint
            )

            Spacer(modifier = Modifier.height(30.dp))

            LabeledTextField(
                labelTextId = R.string.phone_text,
                value = userPhone,
                onValueChange = { userPhone = it },
                placeholderTextId = R.string.phone_hint
            )

            Spacer(modifier = Modifier.weight(2f))

            RoundedCornerButton(buttonText = R.string.signup_btn_text,
                onClick = {
                    onClickSignUpBtn(userId, userPassword, userNickname, userPhone)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SignUpPreview() {
        NOWSOPTAndroidTheme {
            SignUpScreen(onClickSignUpBtn = { _, _, _, _ -> })
        }
    }
}