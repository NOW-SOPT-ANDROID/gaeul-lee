package com.sopt.now.compose.feature.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.feature.main.MainActivity
import com.sopt.now.compose.feature.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.compose.feature.signup.SignUpActivity
import com.sopt.now.compose.remote.request.RequestLoginDto
import com.sopt.now.compose.remote.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun login(context: Context, id: String, password: String) {
        val loginRequest = RequestLoginDto(
            authenticationId = id,
            password = password,
        )
        ServicePool.authService.login(loginRequest).enqueue(object :
            Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>
            ) {
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        context, "로그인 성공 유저의 ID는 $userId 입니둥", Toast.LENGTH_SHORT,
                    ).show()

                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra(LOGIN_INFO, userId)
                    context.startActivity(intent)
                } else {
                    val error = response.message()
                    Toast.makeText(
                        context, "로그인이 실패 $error", Toast.LENGTH_SHORT,
                    ).show()
                }
            }


            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                Toast.makeText(
                    context, t.message.toString(), Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun navigateToSignUp(context: Context) {
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }
}