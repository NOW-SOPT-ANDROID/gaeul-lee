package com.sopt.now.activity

import UserData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sopt.now.R
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.viewmodel.HomeViewModel
import com.sopt.now.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel = SignUpViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUpBtnClick()
    }

    private fun signUpBtnClick() {
        binding.signUpBtn.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pwd = binding.passwordEditText.text.toString()
            val nickname = binding.nicknameEditText.text.toString()
            val mbti = binding.mbtiEditText.text.toString()
            val userInfo = UserData(id, pwd, nickname, mbti)

            val (isPossible, message) = viewModel.isSignUpPossible(userInfo)
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()

            // 회원가입이 성공한 경우
            if(isPossible){
                viewModel.setUserInfo(userInfo) // 사용자 정보 설정
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(USER_INFO, userInfo)
                setResult(RESULT_OK, intent)
                finish() // 화면 전환
            }
        }
    }
}