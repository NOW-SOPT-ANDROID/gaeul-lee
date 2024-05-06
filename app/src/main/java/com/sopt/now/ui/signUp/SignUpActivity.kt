package com.sopt.now.ui.signUp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        signUpBtnClick()
        observeSignUpResult()
    }

    private fun observeSignUpResult() {
        viewModel.signUpResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this@SignUpActivity, R.string.signup_success, Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, R.string.signup_error, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun signUpBtnClick() {
        binding.signUpBtn.setOnClickListener {
            signUpStateChanged()
        }
    }

    private fun signUpStateChanged() {
        viewModel.signUp(
            binding.idEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            binding.nicknameEditText.text.toString(),
            binding.phoneEditText.text.toString()
        )
    }
}