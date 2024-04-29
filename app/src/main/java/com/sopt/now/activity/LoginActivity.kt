package com.sopt.now.activity

import UserData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.RequestLoginDto
import com.sopt.now.RequestSignUpDto
import com.sopt.now.ResponseLoginDto
import com.sopt.now.ResponseSignUpDto
import com.sopt.now.ServicePool
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val users: MutableList<UserData> = mutableListOf()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            val userInfo = activityResult.data?.getParcelableExtra<UserData>(USER_INFO)
            userInfo?.let { users.add(it) }
        }
    }

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

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val error = response.message()
                    Log.e("test", response.message())
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
            resultLauncher.launch(intent)
        }
    }

    private fun loginBtnClick() {
        binding.loginBtn.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pwd = binding.passwordEditText.text.toString()
            val result = isLoginPossible(id, pwd)
            if (result != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(LOGIN_INFO, result)
                startActivity(intent)
            }
        }
    }

    private fun isLoginPossible(id: String, pwd: String): UserData? {
        var result: UserData? = null
        var message = ""
        users.forEach { user ->
            when {
                user.id != id -> {
                    message = getString(R.string.login_id_error)
                }

                user.pwd != pwd -> {
                    message = getString(R.string.login_password_error)
                }

                else -> {
                    result = user
                    message = getString(R.string.login_success)
                }

            }
        }
        if (message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        return result
    }
}