package com.sopt.now.compose.feature.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.data.User
import com.sopt.now.compose.feature.MainActivity
import com.sopt.now.compose.feature.MainActivity.Companion.LOGIN_INFO
import com.sopt.now.compose.feature.changePwd.ChangePwdActivity
import com.sopt.now.compose.feature.login.LoginActivity
import com.sopt.now.compose.remote.response.ResponseUserInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyPageViewModel : ViewModel() {
    fun fetchUserInfo(context: Context, userId: Int, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            try{
                ServicePool.userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
                    override fun onResponse(call: Call<ResponseUserInfoDto>, response: Response<ResponseUserInfoDto>) {
                        if (response.isSuccessful) {
                            val data: ResponseUserInfoDto? = response.body()
                            data?.let {
                                val user = User(it.data.authenticationId, it.data.nickname, it.data.phone)
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
            } catch(e: Exception) {
                onFailure("Failed to fetch user info: ${e.message}")
                Log.e("MyPageViewModel", "Error: ${e.message}")
            }
        }
    }

    fun onClickLogoutBtn(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, intent, null)
    }

    fun onClickChangePwdBtn(context: Context, userId: Int) {
        val intent = Intent(context, ChangePwdActivity::class.java)
        val bundle = Bundle().apply {
            putInt(LOGIN_INFO, userId)
        }
        intent.putExtras(bundle)
        ContextCompat.startActivity(context, intent, null)
    }
}