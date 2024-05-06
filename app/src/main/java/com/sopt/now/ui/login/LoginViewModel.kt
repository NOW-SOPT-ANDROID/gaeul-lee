package com.sopt.now.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.ui.base.ServicePool
import com.sopt.now.remote.request.RequestLoginDto
import com.sopt.now.remote.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: MutableLiveData<Boolean>
        get() = _loginResult

    private val _userId = MutableLiveData<String?>()
    val userId: MutableLiveData<String?>
        get() = _userId

    private val authService by lazy { ServicePool.authService }

    fun login(authenticationId: String, password: String) {
        val loginRequest = RequestLoginDto(
            authenticationId = authenticationId,
            password = password
        )
        authService.login(loginRequest).enqueue(object :
            Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()["location"]
                    Log.d("LoginViewModel", "data: $data, userId: $userId")
                    _loginResult.postValue(true)
                    _userId.value = userId
                } else {
                    _loginResult.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                _loginResult.postValue(false)
            }
        }
        )
    }
}