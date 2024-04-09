package com.sopt.now

import UserData
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.databinding.ActivityMainBinding

const val USER_INFO = "userInfo"
const val LOGIN_INFO = "loginInfo"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user : UserData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra(LOGIN_INFO) as UserData

        with(binding){
            userNickname.text = user.nickname
            userId.text = user.id
            userPassword.text = user.pwd
        }
    }
}