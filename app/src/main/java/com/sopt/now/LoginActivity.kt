package com.sopt.now

import UserData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val users : MutableList<UserData> = mutableListOf()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == RESULT_OK){
            val userInfo = it.data?.getSerializableExtra("userInfo") as UserData
            userInfo?.let{users.add(it)}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val pwd = binding.passwordEditText.text.toString()
            val result = isLoginPossible(id, pwd)
            if(result != null){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("login", users[result])
                startActivity(intent)
            }

        }
        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun isLoginPossible(id: String, pwd: String): Int? {
        var result : Int? = null
        var message = ""
        users.indices.forEach { user ->
            when {
                user.id == id && user.pwd == pwd -> {
                    result = user
                    message = "로그인에 성공했습니다."
                }
                user.id == id && user.pwd != pwd -> {
                    message = "비밀번호가 틀렸습니다."
                }
                user.id != id -> {
                    message = "존재하지 않는 아이디입니다."
                }
            }
        }
        if(message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        return result
    }
}