package com.sopt.now.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.response.ResponseUserInfoDto
import com.sopt.now.data.remote.ServicePool.userService
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<ResponseUserInfoDto>()
    val userInfo: LiveData<ResponseUserInfoDto>
        get() = _userInfo

    fun fetchUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                userService.getUserInfo(userId)
            }.onSuccess {
                _userInfo.postValue(it.body())
            }.onFailure {
                Log.e("MyPageViewModel", it.message.toString())
            }
        }
    }

}