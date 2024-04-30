package com.sopt.now.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.databinding.ActivityChangePwdBinding
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.fragment.MyPageFragment
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.fragment.MyPageFragment.Companion.USER_INFO
import com.sopt.now.request.RequestChangePwdDto
import com.sopt.now.request.RequestSignUpDto
import com.sopt.now.response.ResponseChangePwdDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePwdBinding
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent.getStringExtra(USER_INFO)
        Log.e("ChangePwd", "userId: $userId")
        changePwdBtnClick()
    }

    private fun changePwdBtnClick() {
        binding.btnChangePwd.setOnClickListener {
            // 비밀번호 변경 로직
            changePassword()
        }
    }

    private fun changePassword() {
        val pwdRequest = getChangePwdRequestDto()
        userId?.let {
            ServicePool.userService.changeUserPwd(it.toInt(), pwdRequest).enqueue(object :
                Callback<ResponseChangePwdDto> {
                override fun onResponse(
                    call: Call<ResponseChangePwdDto>,
                    response: Response<ResponseChangePwdDto>,
                ) {
                    if (response.isSuccessful) {
                        val data: ResponseChangePwdDto? = response.body()
                        Toast.makeText(
                            this@ChangePwdActivity,
                            "비밀번호 변경 성공",
                            Toast.LENGTH_SHORT,
                        ).show()
                        Log.e("ChangePwd", "data: $data")
                        finish()
                    } else {
                        val error = response.message()
                        Log.e("test", error)
                        Toast.makeText(
                            this@ChangePwdActivity,
                            "비밀번호 변경 실패 $error",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseChangePwdDto>, t: Throwable) {
                    Log.e("test", t.message.toString())
                    Toast.makeText(
                        this@ChangePwdActivity,
                        "비밀번호 변경 실패",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            })
        }
    }

    private fun getChangePwdRequestDto() : RequestChangePwdDto{
        val previousId = binding.etPreviousPwd.text.toString()
        val newPwd = binding.etNewPwd.text.toString()
        val checkPwd = binding.etCheckPwd.text.toString()
        return RequestChangePwdDto(
            previousPassword = previousId,
            newPassword = newPwd,
            newPasswordVerification = checkPwd,
        )
    }
}