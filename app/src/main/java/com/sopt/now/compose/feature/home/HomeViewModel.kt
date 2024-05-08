package com.sopt.now.compose.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.Friend
import com.sopt.now.compose.remote.response.ResponseFriendsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    fun fetchFriendsInfo(onSuccess: (MutableList<Friend>) -> Unit, onFailure: (String) -> Unit) {
        var friendList = mutableListOf<Friend>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ServicePool.friendService.getFriends(2)
                    .enqueue(object : Callback<ResponseFriendsDto> {

                        override fun onResponse(
                            call: Call<ResponseFriendsDto>,
                            response: Response<ResponseFriendsDto>
                        ) {
                            if (response.isSuccessful) {
                                val friends = response.body()?.data
                                friends?.forEach { friend ->
                                    friendList.add(
                                        Friend(
                                            friend.avatar,
                                            friend.firstName,
                                            friend.email
                                        )
                                    )
                                }
                                onSuccess(friendList)
                            } else {
                                onFailure("Failed to fetch friends")
                            }
                        }

                        override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                            onFailure(t.message ?: "Failed to fetch friends")
                        }
                    })
            } catch (e: Exception) {
                onFailure("Failed to fetch friend info: ${e.message}")
            }
        }
    }
}