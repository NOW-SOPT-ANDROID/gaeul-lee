package com.sopt.now.presentation.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.request.RequestSignUpDto
import com.sopt.now.data.remote.ServicePool.authService
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>
        get() = _signUpState

    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _signUpState.value = SignUpState(true, "회원가입 성공")
                } else {
                    _signUpState.value =
                        it.errorBody()?.string()?.split("\"")?.let { SignUpState(false, it[5]) }
                }
            }.onFailure {
                _signUpState.value = SignUpState(false, "회원가입 실패")
            }
        }
    }

}