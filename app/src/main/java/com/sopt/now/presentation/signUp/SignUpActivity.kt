package com.sopt.now.presentation.signUp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.data.repositoryImpl.AuthRepositoryImpl
import com.sopt.now.data.remote.ServicePool.authService
import com.sopt.now.data.remote.request.RequestSignUpDto
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.domain.AuthRepository

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val authRepository: AuthRepository by lazy { AuthRepositoryImpl(authService) }
    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory(authRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUpBtnClick()
        observeSignUpState()
    }

    private fun observeSignUpState() {
        viewModel.signUpState.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpBtnClick() {
        binding.signUpBtn.setOnClickListener {
            signUpStateChanged()
        }
    }

    private fun signUpStateChanged() {
        viewModel.signUp(
            RequestSignUpDto(
                authenticationId = binding.idEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                nickname = binding.nicknameEditText.text.toString(),
                phone = binding.phoneEditText.text.toString()
            )
        )
    }
}