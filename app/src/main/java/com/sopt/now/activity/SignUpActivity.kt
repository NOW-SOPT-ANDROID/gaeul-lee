package com.sopt.now.activity

import UserData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseSignUpDto
import com.sopt.now.ServicePool.authService
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.viewmodel.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel = SignUpViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.signUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val signUpRequest = getSignUpRequestDto()
        authService.signUp(signUpRequest).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseSignUpDto? = response.body()
                    val userId = response.headers()["location"]
                    Toast.makeText(
                        this@SignUpActivity,
                        "회원가입 성공 유저의 ID는 $userId 입니둥",
                        Toast.LENGTH_SHORT,
                    ).show()
                    Log.d("SignUp", "data: $data, userId: $userId")
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    intent.putExtra(USER_INFO, userId)
                    startActivity(intent)
                } else {
                    val error = response.message()
                    Log.e("test", error)
                    Toast.makeText(
                        this@SignUpActivity,
                        "회원가입 실패 $error",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getSignUpRequestDto(): RequestSignUpDto {
        val id = binding.idEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val nickname = binding.nicknameEditText.text.toString()
        val phoneNumber = binding.phoneEditText.text.toString()
        return RequestSignUpDto(
            authenticationId = id,
            password = password,
            nickname = nickname,
            phone = phoneNumber
        )
    }
}