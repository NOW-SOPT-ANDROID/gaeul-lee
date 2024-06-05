package com.sopt.now.compose.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.FollowerRepository
import com.sopt.now.compose.presentation.home.User
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MyPageViewModel(
    private val followerRepository: FollowerRepository
) : ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    fun fetchUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                followerRepository.getUserInfo(userId)
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
}