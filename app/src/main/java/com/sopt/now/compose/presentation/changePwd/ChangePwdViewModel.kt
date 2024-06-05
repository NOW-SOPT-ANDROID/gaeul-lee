package com.sopt.now.compose.presentation.changePwd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.remote.request.RequestChangePwdDto
import com.sopt.now.compose.domain.FollowerRepository
import kotlinx.coroutines.launch

class ChangePwdViewModel(
    private val followerRepository: FollowerRepository
) : ViewModel() {
    private val _changePwdState = MutableLiveData<ChangePwdState>()
    val changePwdState: LiveData<ChangePwdState>
        get() = _changePwdState

    fun changePwd(userId: Int, request: RequestChangePwdDto) {
        viewModelScope.launch {
            runCatching {
                followerRepository.changeUserPwd(userId, request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _changePwdState.value = ChangePwdState(true, "비밀번호 변경 성공")
                } else {
                    _changePwdState.value =
                        it.errorBody()?.string()?.split("\"")
                            ?.let { ChangePwdState(false, it[5]) }
                }
            }.onFailure {
                _changePwdState.value = ChangePwdState(false, "비밀번호 변경 실패")
            }
        }
    }
}