package com.sopt.now.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.domain.AuthRepository
import com.sopt.now.data.remote.ServicePool.authService
import com.sopt.now.data.remote.request.RequestLoginDto
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRespository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authRespository.login(request)
                //authService.login(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    val userId = it.headers()["location"]
                    _loginState.value = LoginState(true, "로그인 성공", userId)
                } else {
                    _loginState.value =
                        it.errorBody()?.string()?.split("\"")?.let { LoginState(false, it[5]) }
                }
            }.onFailure {
                _loginState.value = LoginState(false, it.message.toString())
            }
        }
    }
}