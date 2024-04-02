package com.sopt.now

import UserData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pwd = binding.passwordEditText.text.toString()
            val nickname = binding.nicknameEditText.text.toString()
            val mbti = binding.mbtiEditText.text.toString()

            if(isSignUpPossible(id, pwd, nickname, mbti)){
                val intent = Intent(this, LoginActivity::class.java)
                val userInfo = UserData(id, pwd, nickname, mbti)
                intent.putExtra("userInfo", userInfo)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun isSignUpPossible(id: String, pwd: String, nickname: String, mbti: String): Boolean {
        var isPossible = false
        val message = when{
            id.length !in 6..10 -> "ID는 6~10글자여야 합니다"
            pwd.length !in 8..12 -> "비밀번호는 8~12글자여야 합니다"
            nickname.isBlank() || nickname.contains(" ") -> "공백 없이 한글자 이상이어야 합니다."
            mbti.isBlank() -> "MBTI를 입력해주세요"
            else -> {
                isPossible = true
                "회원가입이 완료되었습니다."
            }
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return isPossible
    }
}