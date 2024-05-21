package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.remote.request.RequestSignUpDto
import com.sopt.now.ui.base.ServicePool
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _signUpSate = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState>
        get() = _signUpSate

    private val authService by lazy { ServicePool.authService }
    fun signUp(request: RequestSignUpDto) {
        viewModelScope.launch {
            runCatching {
                authService.signUp(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _signUpSate.value = SignUpState(true, "회원가입 성공")
                } else {
                    _signUpSate.value =
                        it.errorBody()?.string()?.split("\"")?.let { SignUpState(false, it[5]) }
                }
            }.onFailure {
                _signUpSate.value = SignUpState(false, "회원가입 실패")
            }
        }
    }

}