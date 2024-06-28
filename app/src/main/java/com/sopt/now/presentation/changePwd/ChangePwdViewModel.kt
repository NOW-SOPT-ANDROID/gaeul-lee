package com.sopt.now.presentation.changePwd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.request.RequestChangePwdDto
import com.sopt.now.domain.FollowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePwdViewModel @Inject constructor(
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
                        it.errorBody()?.string()?.split("\"")?.let { ChangePwdState(false, it[5]) }
                }
            }.onFailure {
                _changePwdState.value = ChangePwdState(false, it.message.toString())
            }
        }
    }
}