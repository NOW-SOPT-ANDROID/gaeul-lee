package com.sopt.now.activity

import UserData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.now.R
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
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

            val (isPossible, message) = isSignUpPossible(id, pwd, nickname, mbti)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            // 회원가입이 성공한 경우
            if(isPossible){
                val intent = Intent(this, LoginActivity::class.java)
                val userInfo = UserData(id, pwd, nickname, mbti)
                intent.putExtra(USER_INFO, userInfo)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun isSignUpPossible(id: String, pwd: String, nickname: String, mbti: String): Pair<Boolean, String> {
        val message = when{
            id.length !in 6..10 -> getString(R.string.signup_id_error)
            pwd.length !in 8..12 -> getString(R.string.signup_password_error)
            nickname.isBlank() || nickname.contains(" ") -> getString(R.string.signup_nickname_error)
            mbti.isBlank() -> getString(R.string.signup_mbti_error)
            else -> {
                return Pair(true, getString(R.string.signup_success))
            }
        }

        return Pair(false, message)
    }
}