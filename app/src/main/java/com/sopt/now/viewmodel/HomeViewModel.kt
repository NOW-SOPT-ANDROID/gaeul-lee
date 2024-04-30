package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.friend.Friend
import com.sopt.now.response.ResponseFriendsDto
import com.sopt.now.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val mockFriendList = mutableListOf<Friend>()

    init {
        uploadFriends()
    }
    private fun uploadFriends(){
        ServicePool.friendService.getFriends(2).enqueue(object : Callback<ResponseFriendsDto> {

            override fun onResponse(
                call: Call<ResponseFriendsDto>,
                response: Response<ResponseFriendsDto>
            ) {
                if (response.isSuccessful) {
                    val friends = response.body()?.data
                    Log.d("HomeViewModel", "friends: $friends")
                    friends?.forEach { friend ->
                        mockFriendList.add(Friend(friend.avatar, friend.firstName, friend.email))
                    }
                } else {
                    Log.d("HomeViewModel", "response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                Log.d("HomeViewModel", "onFailure: ${t.message}")
            }

        })
    }

}