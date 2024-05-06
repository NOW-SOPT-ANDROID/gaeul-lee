package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.ServicePool
import com.sopt.now.friend.Friend
import com.sopt.now.response.ResponseFriendsDto
import com.sopt.now.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _friendList = MutableLiveData<List<Friend>>()
    val friendList : LiveData<List<Friend>>
        get() = _friendList

    private val _userInfo = MutableLiveData<ResponseUserInfoDto>()
    val userInfo: LiveData<ResponseUserInfoDto>
        get() = _userInfo


    private val userService by lazy { ServicePool.userService }
    private val friendService by lazy { ServicePool.friendService }

    fun fetchFriends(userId: Int) {
        friendService.getFriends(userId).enqueue(object : Callback<ResponseFriendsDto> {
            override fun onResponse(call: Call<ResponseFriendsDto>, response: Response<ResponseFriendsDto>) {
                if (response.isSuccessful) {
                    val friends = response.body()?.data ?: emptyList()
                    _friendList.postValue(friends.map { Friend(it.avatar, it.firstName, it.email) })
                }
            }

            override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }
        })
    }

    fun fetchUserInfo(userId: Int) {
        userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(call: Call<ResponseUserInfoDto>, response: Response<ResponseUserInfoDto>) {
                if (response.isSuccessful) {
                    _userInfo.postValue(response.body())
                } else {
                    Log.e("HomeViewModel", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }
        })
    }

}