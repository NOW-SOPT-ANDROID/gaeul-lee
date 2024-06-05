package com.sopt.now.presentation.changePwd

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.data.remote.ServicePool.friendService
import com.sopt.now.data.remote.ServicePool.userService
import com.sopt.now.data.remote.request.RequestChangePwdDto
import com.sopt.now.data.repositoryImpl.FollowerRepositoryImpl
import com.sopt.now.databinding.ActivityChangePwdBinding
import com.sopt.now.domain.FollowerRepository
import com.sopt.now.presentation.main.MainViewModel.Companion.USER_INFO

class ChangePwdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePwdBinding
    private val followerRepository: FollowerRepository by lazy {
        FollowerRepositoryImpl(
            userService,
            friendService
        )
    }
    private val viewModel: ChangePwdViewModel by viewModels {
        ChangePwdViewModelFactory(followerRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changePwdBtnClick()
        observeChangePwdState()
    }

    private fun observeChangePwdState() {
        viewModel.changePwdState.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changePwdBtnClick() {
        binding.btnChangePwd.setOnClickListener {
            val userId = intent.getStringExtra(USER_INFO)?.toInt() ?: 0
            viewModel.changePwd(
                userId,
                RequestChangePwdDto(
                    previousPassword = binding.etPreviousPwd.text.toString(),
                    newPassword = binding.etNewPwd.text.toString(),
                    newPasswordVerification = binding.etNewPwdVerification.text.toString()
                )
            )
        }
    }
}