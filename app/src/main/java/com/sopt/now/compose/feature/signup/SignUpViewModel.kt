package com.sopt.now.compose.feature.signup

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.remote.request.RequestSignUpDto
import com.sopt.now.compose.remote.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    fun signUp(context: Context, id: String, password: String, nickname: String, phone: String) {
        val signUpRequest = RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phone
        )
        ServicePool.authService.signUp(signUpRequest).enqueue(object :
            Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        context, "회원가입 성공 유저의 ID는 $userId 입니다.", Toast.LENGTH_SHORT
                    ).show()
                    (context as? ComponentActivity)?.finish()
                } else {
                    val error = response.message()
                    Toast.makeText(
                        context, "회원가입 실패 $error", Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                Toast.makeText(
                    context,
                    "서버 통신 실패 ${t.message}",
                    Toast.LENGTH_SHORT,
                ).show()
            }

        })
    }
}