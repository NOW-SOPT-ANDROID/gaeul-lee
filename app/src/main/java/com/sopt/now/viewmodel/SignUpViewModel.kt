package com.sopt.now.viewmodel

import UserData
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R

class SignUpViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<UserData>()
    val userInfo : LiveData<UserData>
        get() = _userInfo
    fun setUserInfo(userInfo: UserData){
        _userInfo.value = userInfo
    }

    fun isSignUpPossible(user : UserData) : Pair<Boolean, Int>{
        val message = when{
            user.id.length !in MIN_ID_LENGTH..MAX_ID_LENGTH -> R.string.signup_id_error
            user.pwd.length !in MIN_PWD_LENGTH..MAX_PWD_LENGTH -> R.string.signup_password_error
            user.nickname.isBlank() || user.nickname.contains(" ") -> R.string.signup_nickname_error
            user.mbti.isBlank() -> R.string.signup_mbti_error
            else -> {
                return Pair(true, R.string.signup_success)
            }
        }

        return Pair(false, message)
    }

    companion object {
        private const val MIN_ID_LENGTH = 6
        private const val MAX_ID_LENGTH = 10
        private const val MIN_PWD_LENGTH = 8
        private const val MAX_PWD_LENGTH = 12
    }
}