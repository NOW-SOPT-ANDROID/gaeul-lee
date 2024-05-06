package com.sopt.now.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.databinding.ActivityChangePwdBinding
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.request.RequestChangePwdDto
import com.sopt.now.response.ResponseChangePwdDto
import com.sopt.now.viewmodel.ChangePwdViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePwdBinding
    private var userId: String? = null
    private lateinit var viewModel : ChangePwdViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChangePwdViewModel::class.java)

        userId = intent.getStringExtra(USER_INFO)
        onClick()

        observeChangePwdResult()
    }

    private fun observeChangePwdResult() {
        viewModel.changePwdResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this@ChangePwdActivity, R.string.change_pwd_success, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@ChangePwdActivity, R.string.change_pwd_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onClick() {
        binding.btnChangePwd.setOnClickListener {
            // 비밀번호 변경 로직
            onPasswordChange()
        }
    }

    private fun onPasswordChange() {
        userId?.let {
            viewModel.changePassword(
                it.toInt(),
                binding.etPreviousPwd.text.toString(),
                binding.etNewPwd.text.toString(),
                binding.etCheckPwd.text.toString()
            )
        }
    }
}