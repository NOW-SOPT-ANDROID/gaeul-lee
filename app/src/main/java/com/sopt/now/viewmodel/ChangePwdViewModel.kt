package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.api.ServicePool
import com.sopt.now.request.RequestChangePwdDto
import com.sopt.now.response.ResponseChangePwdDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdViewModel: ViewModel() {
    private val _changePwdResult = MutableLiveData<Boolean>()
    val changePwdResult: LiveData<Boolean>
        get() = _changePwdResult

    private val userService by lazy { ServicePool.userService}

    fun changePassword(userId: Int, previousPassword: String, newPassword: String, newPasswordVerification: String) {
        val pwdRequest = RequestChangePwdDto(
            previousPassword = previousPassword,
            newPassword = newPassword,
            newPasswordVerification = newPasswordVerification
        )
        userService.changeUserPwd(userId, pwdRequest).enqueue(object :
            Callback<ResponseChangePwdDto> {
            override fun onResponse(
                call: Call<ResponseChangePwdDto>,
                response: Response<ResponseChangePwdDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseChangePwdDto? = response.body()
                    Log.d("ChangePwdViewModel", "data: $data, userId: $userId")
                    _changePwdResult.postValue(true)
                } else {
                    _changePwdResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseChangePwdDto>, t: Throwable) {
                _changePwdResult.postValue(false)
            }
        })
    }
}