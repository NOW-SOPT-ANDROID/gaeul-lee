package com.sopt.now.compose.presentation.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.remote.request.RequestLoginDto
import com.sopt.now.compose.domain.AuthRepository
import com.sopt.now.compose.util.PreferencesUtil
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun login(context: Context, request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authRepository.login(request)
            }.onSuccess { it ->
                if (it.code() in 200..299) {
                    val userId = it.headers()["location"]
                    _loginState.value = LoginState(true, "로그인 성공", userId)
                    userId?.let { id -> PreferencesUtil.saveUserId(context, id) }
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
}