package com.sopt.now.compose.feature.changePwd

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.ServicePool
import com.sopt.now.compose.remote.request.RequestChangePwdDto
import com.sopt.now.compose.remote.response.ResponseChangePwdDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePwdViewModel : ViewModel() {
    fun changePwd(context: Context, userId: Int, previousPwd: String, newPwd: String, newPwdVerification: String) {
        val changePwdRequest =
            RequestChangePwdDto(
                previousPassword = previousPwd,
                newPassword = newPwd,
                newPasswordVerification = newPwdVerification
            )
        userId?.let {
            ServicePool.userService.changeUserPwd(it, changePwdRequest)
                .enqueue(object :
                    Callback<ResponseChangePwdDto> {
                    override fun onResponse(
                        call: Call<ResponseChangePwdDto>,
                        response: Response<ResponseChangePwdDto>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "비밀번호 변경 성공", Toast.LENGTH_SHORT
                            ).show()
                            (context as? ComponentActivity)?.finish()
                        } else {
                            val error = response.message()
                            Toast.makeText(context, "비밀번호 변경 실패 $error", Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseChangePwdDto>,
                        t: Throwable
                    ) {
                        Toast.makeText(context, "서버 통신 실패 ${t.message}", Toast.LENGTH_SHORT,
                        ).show()
                    }
                })
        }
    }
}