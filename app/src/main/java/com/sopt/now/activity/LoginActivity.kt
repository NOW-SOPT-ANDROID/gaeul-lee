package com.sopt.now.activity

import UserData
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val users : MutableList<UserData> = mutableListOf()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        activityResult ->
        if(activityResult.resultCode == RESULT_OK){
            val userInfo = activityResult.data?.getSerializableExtra(USER_INFO) as? UserData
            userInfo?.let{users.add(it)}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginBtnClick()
        signUpBtnClick()
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
            if(result != null){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(LOGIN_INFO, result)
                startActivity(intent)
            }
        }
    }

    private fun isLoginPossible(id: String, pwd: String): UserData? {
        var result : UserData? = null
        var message = ""
        users.forEach { user ->
            when {
                user.id != id -> {
                    message = "존재하지 않는 아이디입니다."
                }
                user.pwd != pwd -> {
                    message = "비밀번호가 틀렸습니다."
                }
                else -> {
                    result = user
                    message = "로그인에 성공했습니다."
                }

            }
        }
        if(message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        return result
    }
}