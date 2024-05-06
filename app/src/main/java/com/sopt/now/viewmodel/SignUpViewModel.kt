package com.sopt.now.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.api.ServicePool
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: MutableLiveData<Boolean>
        get() = _signUpResult

    private val authService by lazy { ServicePool.authService }

    fun signUp(authenticationId: String, password: String, nickname: String, phone: String) {
        val signUpRequest = RequestSignUpDto(
            authenticationId = authenticationId,
            password = password,
            nickname = nickname,
            phone = phone
        )
        authService.signUp(signUpRequest).enqueue(object :
            Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    Log.d("SignUpViewModel", "data: $data, userId: $userId")
                    _signUpResult.postValue(true)
                } else {
                    _signUpResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                _signUpResult.postValue(false)
            }
        }
        )
    }

}