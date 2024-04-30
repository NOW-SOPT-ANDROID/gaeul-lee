package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.ServicePool
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.request.RequestLoginDto
import com.sopt.now.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.signUpBtn.setOnClickListener {
            signUpBtnClick()
        }
    }

    private fun getLoginRequestDto(): RequestLoginDto {
        val id = binding.idEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        return RequestLoginDto(
            authenticationId = id,
            password = password,
        )
    }

    private fun login() {
        val loginRequest = getLoginRequestDto()
        ServicePool.authService.login(loginRequest).enqueue(object : Callback<ResponseLoginDto> {
            override fun onResponse(
                call: Call<ResponseLoginDto>,
                response: Response<ResponseLoginDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseLoginDto? = response.body()
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        this@LoginActivity,
                        "로그인 성공 유저의 ID는 $userId 입니둥",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d("login", "data: $data, userId: $userId")

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(LOGIN_INFO, userId)
                    startActivity(intent)
                } else {
                    val error = response.message()
                    Toast.makeText(
                        this@LoginActivity,
                        "로그인이 실패 $error",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun signUpBtnClick() {
        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}