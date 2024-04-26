package com.sopt.now.activity

import UserData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO

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