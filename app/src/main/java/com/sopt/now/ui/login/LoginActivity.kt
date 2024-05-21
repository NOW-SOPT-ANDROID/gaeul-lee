package com.sopt.now.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.remote.request.RequestLoginDto
import com.sopt.now.ui.main.MainActivity
import com.sopt.now.ui.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.ui.signUp.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginBtnClick()
        observeLoginState()
        signUpBtnClick()
        backPressed()
    }

    private fun observeLoginState() {
        viewModel.loginState.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                viewModel.userId.observe(this) {
                    intent.putExtra(LOGIN_INFO, viewModel.userId.value)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginBtnClick() {
        binding.loginBtn.setOnClickListener {
            loginStateChanged()
        }
    }

    private fun loginStateChanged() {
        viewModel.login(
            RequestLoginDto(
                authenticationId = binding.idEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        )
    }

    private fun signUpBtnClick() {
        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
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

}