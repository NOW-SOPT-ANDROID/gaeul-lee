package com.sopt.now.compose.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.remote.request.RequestSignUpDto
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>
        get() = _signUpState
    private val authService by lazy { ServicePool.authService }
    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _signUpState.value = SignUpState(true, "회원가입 성공")
                } else {
                    _signUpState.value =
                        it.errorBody()?.string()?.split("\"")
                            ?.let { SignUpState(false, it[5]) }
                }
            }.onFailure {
                _signUpState.value = SignUpState(false, "회원가입 실패")
            }
        }
    }

}