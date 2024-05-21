package com.sopt.now.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.remote.request.RequestLoginDto
import com.sopt.now.ui.base.ServicePool
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
            }.onSuccess {
                val userId = it.headers()["location"]
                _loginState.value = LoginState(true, "로그인 성공")
                _userId.value = userId.toString()
            }.onFailure {
                if (it is HttpException) {
                    _loginState.value = LoginState(false, "서버통신 실패")
                } else {
                    _loginState.value = LoginState(false, "로그인 실패")
                }
            }
        }
    }
}