package com.sopt.now.compose.presentation.login

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.R
import com.sopt.now.compose.data.remote.ServicePool.authService
import com.sopt.now.compose.data.remote.request.RequestLoginDto
import com.sopt.now.compose.data.repositoryImpl.AuthRepositoryImpl
import com.sopt.now.compose.domain.AuthRepository
import com.sopt.now.compose.presentation.main.MainActivity
import com.sopt.now.compose.presentation.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.compose.presentation.signup.SignUpActivity
import com.sopt.now.compose.util.BaseViewModelFactory
import com.sopt.now.compose.util.LabeledTextField
import com.sopt.now.compose.util.NOWSOPTAndroidTheme
import com.sopt.now.compose.util.PreferencesUtil
import com.sopt.now.compose.util.RoundedCornerButton

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

fun navigateToMain(context: Context, userId: String?) {
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra(LOGIN_INFO, userId)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(context, intent, null)
}

@Composable
fun LoginScreen() {
    var id by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    val context = LocalContext.current

    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(authService) }
    val viewModel: LoginViewModel =
        viewModel(factory = BaseViewModelFactory { LoginViewModel(authRepository) })
    val loginState = viewModel.loginState.observeAsState()

    if (PreferencesUtil.getUserId(context) != null) {
        navigateToMain(context, PreferencesUtil.getUserId(context))
    }

    loginState.value?.let {
        if (it.isSuccess) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            navigateToMain(context, it.userId)
        } else {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

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
                viewModel.login(
                    context,
                    RequestLoginDto(
                        authenticationId = id,
                        password = pwd
                    )
                )
            }
        )
        RoundedCornerButton(
            buttonText = R.string.signup_btn_text,
            onClick = {
                val intent = Intent(context, SignUpActivity::class.java)
                startActivity(context, intent, null)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    NOWSOPTAndroidTheme {
        LoginScreen()
    }
}