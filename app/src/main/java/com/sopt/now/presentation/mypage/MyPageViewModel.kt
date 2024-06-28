package com.sopt.now.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.response.ResponseUserInfoDto
import com.sopt.now.domain.FollowerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val followerRepository: FollowerRepository
) : ViewModel() {
    private val _userInfo = MutableLiveData<ResponseUserInfoDto>()
    val userInfo: LiveData<ResponseUserInfoDto>
        get() = _userInfo

    fun fetchUserInfo(userId: Int) {
        viewModelScope.launch {
            runCatching {
                followerRepository.getUserInfo(userId)
            }.onSuccess {
                _userInfo.postValue(it.body())
            }.onFailure {
                Log.e("MyPageViewModel", it.message.toString())
            }
        }
    }

}