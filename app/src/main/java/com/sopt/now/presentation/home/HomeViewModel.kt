package com.sopt.now.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.ServicePool.friendService
import com.sopt.now.data.remote.ServicePool.userService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {
    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>>
        get() = _friends

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    fun fetchFriends(page: Int) {
        viewModelScope.launch {
            runCatching {
                friendService.getFriends(page)
            }.onSuccess {
                val friends = it.body()?.data ?: emptyList()
                _friends.postValue(friends.map { Friend(it.avatar, it.firstName, it.email) })
            }.onFailure {
                if (it is HttpException) {
                    Log.e("HomeViewModel", "서버통신 오류")
                } else {
                    Log.e("HomeViewModel", it.message.toString())
                }

            }

        }
    }

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

}