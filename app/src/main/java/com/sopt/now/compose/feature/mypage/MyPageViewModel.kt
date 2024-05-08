package com.sopt.now.compose.feature.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.sopt.now.compose.feature.changePwd.ChangePwdActivity
import com.sopt.now.compose.feature.login.LoginActivity
import com.sopt.now.compose.feature.main.MainViewModel.Companion.LOGIN_INFO

class MyPageViewModel : ViewModel() {

    fun onClickLogoutBtn(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, intent, null)
    }

    fun onClickChangePwdBtn(context: Context, userId: Int) {
        val intent = Intent(context, ChangePwdActivity::class.java)
        val bundle = Bundle().apply {
            putInt(LOGIN_INFO, userId)
        }
        intent.putExtras(bundle)
        ContextCompat.startActivity(context, intent, null)
    }
}