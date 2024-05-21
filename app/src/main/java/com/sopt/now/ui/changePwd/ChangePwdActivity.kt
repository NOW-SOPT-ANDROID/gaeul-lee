package com.sopt.now.ui.changePwd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.databinding.ActivityChangePwdBinding
import com.sopt.now.remote.request.RequestChangePwdDto
import com.sopt.now.ui.main.MainActivity
import com.sopt.now.ui.main.MainViewModel.Companion.USER_INFO

class ChangePwdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePwdBinding
    private var userId: String? = null
    private val viewModel by viewModels<ChangePwdViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra(USER_INFO)
        changePwdBtnClick()
        observeChangePwdResult()
    }

    private fun observeChangePwdResult() {
        viewModel.changePwdState.observe(this) {
            if(it.isSuccess) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changePwdBtnClick() {
        binding.btnChangePwd.setOnClickListener {
            userId?.let {
                viewModel.changePwd(
                    it.toInt(),
                    RequestChangePwdDto(
                        previousPassword = binding.etPreviousPwd.text.toString(),
                        newPassword = binding.etNewPwd.text.toString(),
                        newPasswordVerification = binding.etNewPwdVerification.text.toString()
                    )
                )
            }
        }
    }
}