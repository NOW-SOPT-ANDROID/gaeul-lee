package com.sopt.now.compose.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.Friend
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel : ViewModel() {

    private val _friendList = MutableLiveData<List<Friend>>()
    val friendList: LiveData<List<Friend>>
        get() = _friendList
    private val friendService by lazy { ServicePool.friendService }

    fun fetchFriends(page: Int) {
        viewModelScope.launch {
            runCatching {
                friendService.getFriends(page)
            }.onSuccess {
                val friends = it.body()?.data ?: emptyList()
                _friendList.postValue(friends.map { Friend(it.avatar, it.firstName, it.email) })
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