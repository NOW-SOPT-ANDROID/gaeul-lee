package com.sopt.now.compose.feature.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.feature.signup.SignUpActivity
import com.sopt.now.compose.remote.request.RequestLoginDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String>
        get() = _userId

    private val authService by lazy { ServicePool.authService }

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authService.login(request)
            }.onSuccess { it ->
                if(it.code() in 200..299) {
                    _loginState.value = LoginState(true, "로그인 성공")
                    val userId = it.headers()["location"]
                    _userId.value = userId.toString()
                } else {
                    _loginState.value =
                        it.errorBody()?.string()?.split("\"")
                            ?.let { LoginState(false, it[5]) }
                }
            }.onFailure {
                _loginState.value = LoginState(false, it.message.toString())
            }
        }
    }

    fun navigateToSignUp(context: Context) {
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }
}