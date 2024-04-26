package com.sopt.now.compose

import android.app.Activity
import android.content.Context
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
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton

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
                    SignUpScreen(onClickSignUpBtn = { id, pwd, nickname, mbti ->
                        val isPossible = isSignUpPossible(this, id, pwd, nickname, mbti)
                        if (isPossible) {
                            val userInfo = User(id, pwd, nickname, mbti)
                            val intent = Intent()
                            intent.putExtra("USER_INFO", userInfo)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    })
                }
            }
        }
    }

    private fun isSignUpPossible(
        context: Context,
        userId: String,
        userPassword: String,
        userNickname: String,
        userMBTI: String
    ): Boolean {
        val message = when {
            userId.length !in MIN_ID_LENGTH..MAX_ID_LENGTH -> getString(R.string.signup_id_error)
            userPassword.length !in MIN_PW_LENGTH..MAX_PW_LENGTH -> getString(R.string.signup_pw_error)
            userNickname.isBlank() || userNickname.contains(" ") -> getString(R.string.signup_nickname_error)
            userMBTI.isBlank() -> getString(R.string.signup_mbti_error)
            else -> {
                Toast.makeText(this, getString(R.string.signup_success), Toast.LENGTH_SHORT).show()
                return true
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return false
    }

    @Composable
    fun SignUpScreen(
        onClickSignUpBtn: (String, String, String, String) -> Unit
    ) {
        var userId by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }
        var userNickname by remember { mutableStateOf("") }
        var userMBTI by remember { mutableStateOf("") }

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
                labelTextId = R.string.mbti_text,
                value = userMBTI,
                onValueChange = { userMBTI = it },
                placeholderTextId = R.string.mbti_hint
            )

            Spacer(modifier = Modifier.weight(2f))

            RoundedCornerButton(buttonText = R.string.signup_btn_text,
                onClick = {
                    onClickSignUpBtn(userId, userPassword, userNickname, userMBTI)
                }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PW_LENGTH = 8
        const val MAX_PW_LENGTH = 12
    }

    @Preview(showBackground = true)
    @Composable
    fun SignUpPreview() {
        NOWSOPTAndroidTheme {
            SignUpScreen(onClickSignUpBtn = { id, pwd, nickname, mbti -> })
        }
    }
}