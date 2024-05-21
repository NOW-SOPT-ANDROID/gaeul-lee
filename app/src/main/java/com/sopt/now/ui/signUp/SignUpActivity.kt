package com.sopt.now.ui.signUp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.remote.request.RequestSignUpDto

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUpBtnClick()
        observeSignUpState()
    }

    private fun observeSignUpState() {
        viewModel.signUpState.observe(this) {
            if(it.isSuccess) {
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