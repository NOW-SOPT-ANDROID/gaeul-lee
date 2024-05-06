package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.ServicePool
import com.sopt.now.friend.Friend
import com.sopt.now.response.ResponseFriendsDto
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val friendList = mutableListOf<Friend>()

    fun uploadFriends() {
        viewModelScope.launch {
            ServicePool.friendService.getFriends(2).enqueue(object : Callback<ResponseFriendsDto> {

                override fun onResponse(
                    call: Call<ResponseFriendsDto>,
                    response: Response<ResponseFriendsDto>
                ) {
                    if (response.isSuccessful) {
                        val friends = response.body()?.data
                        friends?.forEach { friend ->
                            friendList.add(Friend(friend.avatar, friend.firstName, friend.email))
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

}