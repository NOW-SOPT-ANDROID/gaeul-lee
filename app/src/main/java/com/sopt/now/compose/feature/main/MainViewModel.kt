package com.sopt.now.compose.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.User
import com.sopt.now.compose.remote.response.ResponseUserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun fetchUserInfo(userId: Int, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ServicePool.userService.getUserInfo(userId)
                    .enqueue(object : Callback<ResponseUserInfoDto> {
                        override fun onResponse(
                            call: Call<ResponseUserInfoDto>,
                            response: Response<ResponseUserInfoDto>
                        ) {
                            if (response.isSuccessful) {
                                val data: ResponseUserInfoDto? = response.body()
                                data?.let {
                                    val user = User(
                                        it.data.authenticationId,
                                        it.data.nickname,
                                        it.data.phone
                                    )
                                    onSuccess(user)
                                } ?: onFailure("User data is null")
                            } else {
                                val error = response.errorBody()?.string()
                                onFailure("Failed to fetch user info: $error")
                            }
                        }

                        override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                            onFailure("Failed to fetch user info: ${t.message}")
                        }
                    })
            } catch (e: Exception) {
                onFailure("Failed to fetch user info: ${e.message}")
            }
        }
    }

    companion object {
        const val LOGIN_INFO = "LoginInfo"
    }
}
