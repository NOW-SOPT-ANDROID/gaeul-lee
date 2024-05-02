package com.sopt.now.compose.activity

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.R
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.activity.MainActivity.Companion.LOGIN_INFO
import com.sopt.now.compose.request.RequestChangePwdDto
import com.sopt.now.compose.response.ResponseChangePwdDto
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 번들에서 userId 꺼내기
                    val bundle = intent.extras
                    val userId = bundle?.getInt(LOGIN_INFO)
                    ChangePwdScreen(onClickChangePwdBtn = { previousPwd, newPwd, newPwdVerification ->
                        val changePwdRequest =
                            getChangePwdRequestDto(previousPwd, newPwd, newPwdVerification)
                        userId?.let {
                            ServicePool.userService.changeUserPwd(it, changePwdRequest)
                                .enqueue(object :
                                    Callback<ResponseChangePwdDto> {
                                    override fun onResponse(
                                        call: Call<ResponseChangePwdDto>,
                                        response: Response<ResponseChangePwdDto>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(
                                                this@ChangePwdActivity,
                                                "비밀번호 변경 성공",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            finish()
                                        } else {
                                            val error = response.message()
                                            Toast.makeText(
                                                this@ChangePwdActivity,
                                                "비밀번호 변경 실패 $error",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ResponseChangePwdDto>,
                                        t: Throwable
                                    ) {
                                        Toast.makeText(
                                            this@ChangePwdActivity,
                                            "서버 통신 실패 ${t.message}",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                })
                        }
                    })
                }
            }
        }
    }

    private fun getChangePwdRequestDto(
        previousId: String,
        newPwd: String,
        checkPwd: String
    ): RequestChangePwdDto {
        return RequestChangePwdDto(
            previousPassword = previousId,
            newPassword = newPwd,
            newPasswordVerification = checkPwd,
        )
    }

    @Composable
    fun ChangePwdScreen(
        onClickChangePwdBtn: (String, String, String) -> Unit
    ) {
        var previousPwd by remember { mutableStateOf("") }
        var newPwd by remember { mutableStateOf("") }
        var newPwdVerification by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            LabeledTextField(
                labelTextId = R.string.previous_pwd_text,
                value = previousPwd,
                onValueChange = { previousPwd = it },
                placeholderTextId = R.string.previous_pwd_hint
            )
            Spacer(modifier = Modifier.height(30.dp))
            LabeledTextField(
                labelTextId = R.string.new_pwd_text,
                value = newPwd,
                onValueChange = { newPwd = it },
                placeholderTextId = R.string.new_pwd_hint
            )
            Spacer(modifier = Modifier.height(30.dp))
            LabeledTextField(
                labelTextId = R.string.new_pwd_verification_text,
                value = newPwdVerification,
                onValueChange = { newPwdVerification = it },
                placeholderTextId = R.string.new_pwd_verification_hint
            )
            Spacer(modifier = Modifier.weight(1f))

            RoundedCornerButton(buttonText = R.string.change_pwd_btn_text,
                onClick = {
                    onClickChangePwdBtn(previousPwd, newPwd, newPwdVerification)
                }
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun SignUpPreview() {
        NOWSOPTAndroidTheme {
            ChangePwdScreen(onClickChangePwdBtn = { _, _, _ -> })
        }
    }
}