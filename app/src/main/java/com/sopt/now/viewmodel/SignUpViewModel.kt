package com.sopt.now.viewmodel

import UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.now.R

class SignUpViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<UserData>()


    companion object {
        private const val MIN_ID_LENGTH = 6
        private const val MAX_ID_LENGTH = 10
        private const val MIN_PWD_LENGTH = 8
        private const val MAX_PWD_LENGTH = 12
    }
}