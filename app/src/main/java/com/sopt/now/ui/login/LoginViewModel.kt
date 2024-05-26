package com.sopt.now.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.remote.request.RequestLoginDto
import com.sopt.now.ui.base.ServicePool.authService
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String>
        get() = _userId

    fun login(request: RequestLoginDto) {
        viewModelScope.launch {
            runCatching {
                authService.login(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _loginState.value = LoginState(true, "로그인 성공")
                    val userId = it.headers()["location"]
                    _userId.value = userId.toString()
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