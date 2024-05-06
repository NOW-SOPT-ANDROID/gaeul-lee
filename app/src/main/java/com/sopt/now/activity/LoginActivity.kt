package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.viewmodel.LoginViewModel
import com.sopt.now.viewmodel.MainViewModel.Companion.LOGIN_INFO

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginBtnClick()
        observeLoginResult()
        signUpBtnClick()
        backPressed()
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(LOGIN_INFO, viewModel.userId.value)
                startActivity(intent)
            } else {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
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
            binding.idEditText.text.toString(),
            binding.passwordEditText.text.toString(),
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