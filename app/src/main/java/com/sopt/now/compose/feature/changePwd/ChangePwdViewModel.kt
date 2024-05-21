package com.sopt.now.compose.feature.changePwd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.remote.request.RequestChangePwdDto
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ChangePwdViewModel : ViewModel() {
    private val _changePwdState = MutableLiveData<ChangePwdState>()
    val changePwdState: LiveData<ChangePwdState>
        get() = _changePwdState

    private val userService by lazy { ServicePool.userService }

    fun changePwd(userId: Int, request: RequestChangePwdDto) {
        viewModelScope.launch {
            runCatching {
                userService.changeUserPwd(userId, request)
            }.onSuccess {
                _changePwdState.value = ChangePwdState(true, "비밀번호 변경 성공")
            }.onFailure {
                if (it is HttpException) {
                    _changePwdState.value = ChangePwdState(false, "서버통신 실패")
                } else {
                    _changePwdState.value = ChangePwdState(false, "비밀번호 변경 실패")
                }
            }
        }
    }
}