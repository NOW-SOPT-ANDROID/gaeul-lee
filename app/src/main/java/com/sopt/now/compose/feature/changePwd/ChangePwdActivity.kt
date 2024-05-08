package com.sopt.now.compose.feature.changePwd

import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton

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
                    ChangePwdScreen(userId!!)
                    }
                }
            }
        }
    }

    @Composable
    fun ChangePwdScreen(userId: Int) {
        var previousPwd by remember { mutableStateOf("") }
        var newPwd by remember { mutableStateOf("") }
        var newPwdVerification by remember { mutableStateOf("") }

        val context = LocalContext.current

        val changePwdViewModel =
            ViewModelProvider(context as ComponentActivity)[ChangePwdViewModel::class.java]

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
                    changePwdViewModel.changePwd(
                        context, userId, previousPwd, newPwd, newPwdVerification
                    )
                }
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun SignUpPreview() {
        NOWSOPTAndroidTheme {
            ChangePwdScreen(0)
        }
    }
