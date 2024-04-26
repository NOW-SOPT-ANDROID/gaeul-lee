package com.sopt.now.compose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.sopt.now.compose.ui.theme.LabeledTextField
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import com.sopt.now.compose.ui.theme.RoundedCornerButton

class LoginActivity : ComponentActivity() {
    private val users: MutableList<User> = mutableListOf()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val userInfo = result.data?.getSerializableExtra("USER_INFO") as? User
            userInfo?.let {
                users.add(it)
            }
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
                        onClickLoginBtn = { id, pwd ->
                            val result = isLoginPossible(id, pwd)
                            if (result != null) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("LOGIN_INFO", result)
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


    fun isLoginPossible(id: String, pwd: String): User? {
        var result: User? = null
        var message = ""
        users.forEach { user ->
            when {
                user.id != id -> {
                    message = getString(R.string.login_id_error)
                }

                user.pwd != pwd -> {
                    message = getString(R.string.login_pw_error)
                }

                else -> {
                    result = user
                    message = getString(R.string.login_success)
                }

            }
        }
        if (message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        return result
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