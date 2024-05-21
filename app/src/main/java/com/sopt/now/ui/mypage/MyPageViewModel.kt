package com.sopt.now.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.ui.base.ServicePool
import com.sopt.now.remote.response.ResponseUserInfoDto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<ResponseUserInfoDto>()
    val userInfo: LiveData<ResponseUserInfoDto>
        get() = _userInfo

    private val userService by lazy { ServicePool.userService }
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