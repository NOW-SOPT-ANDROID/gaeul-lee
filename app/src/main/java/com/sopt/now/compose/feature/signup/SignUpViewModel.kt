package com.sopt.now.compose.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.remote.request.RequestSignUpDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

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
                _signUpSate.value = SignUpState(true, "회원가입 성공")
            }.onFailure {
                if (it is HttpException) {
                    _signUpSate.value = SignUpState(false, "서버통신 실패")
                } else {
                    _signUpSate.value = SignUpState(false, "회원가입 실패")
                }

            }
        }

    }

}