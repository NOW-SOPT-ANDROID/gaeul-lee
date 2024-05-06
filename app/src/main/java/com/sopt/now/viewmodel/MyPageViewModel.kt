package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.api.ServicePool
import com.sopt.now.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<ResponseUserInfoDto>()
    val userInfo: LiveData<ResponseUserInfoDto>
        get() = _userInfo

    private val userService by lazy { ServicePool.userService }

    fun fetchUserInfo(userId: Int) {
        userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>
            ) {
                if (response.isSuccessful) {
                    _userInfo.postValue(response.body())
                } else {
                    Log.e("MyPageViewModel", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.e("MyPageViewModel", t.message.toString())
            }
        })
    }

}