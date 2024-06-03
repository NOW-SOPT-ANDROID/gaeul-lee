package com.sopt.now.compose.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.User
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo
    private val userService by lazy { ServicePool.userService }
    fun fetchUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                userService.getUserInfo(userId)
            }.onSuccess {
                _userInfo.value = it.body()?.data
            }.onFailure {
                if (it is HttpException) {
                    Log.e("HomeViewModel", "서버통신 오류")
                } else {
                    Log.e("HomeViewModel", it.message.toString())
                }
            }
        }
    }

    companion object {
        const val LOGIN_INFO = "LoginInfo"
    }
}
