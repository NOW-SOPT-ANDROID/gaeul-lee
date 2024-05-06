package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.request.RequestLoginDto
import com.sopt.now.response.ResponseLoginDto
import com.sopt.now.viewmodel.MainViewModel.Companion.LOGIN_INFO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        backPressed()
        initViews()
    }

    private fun backPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@LoginActivity)
                    .setTitle("앱 종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setIcon(R.drawable.ic_pets_pink_24)
                    .setPositiveButton("확인") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }

        })
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