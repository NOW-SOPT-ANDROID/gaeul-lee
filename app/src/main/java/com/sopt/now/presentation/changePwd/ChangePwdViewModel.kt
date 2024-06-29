package com.sopt.now.presentation.changePwd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.dto.request.RequestChangePwdDto
import com.sopt.now.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePwdViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _changePwdState = MutableLiveData<ChangePwdState>()
    val changePwdState: LiveData<ChangePwdState>
        get() = _changePwdState

    fun changePwd(userId: Int, request: RequestChangePwdDto) {
        viewModelScope.launch {
            runCatching {
                userRepository.changeUserPwd(userId, request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _changePwdState.value = ChangePwdState(true, "비밀번호 변경 성공")
                } else {
                    _changePwdState.value =
                        it.errorBody()?.string()?.split("\"")?.let { ChangePwdState(false, it[5]) }
                }
            }.onFailure {
                _changePwdState.value = ChangePwdState(false, it.message.toString())
            }
        }
    }
}