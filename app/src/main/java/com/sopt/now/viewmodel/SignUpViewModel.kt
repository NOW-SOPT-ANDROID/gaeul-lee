package com.sopt.now.viewmodel

import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    companion object {
        private const val MIN_ID_LENGTH = 6
        private const val MAX_ID_LENGTH = 10
        private const val MIN_PWD_LENGTH = 8
        private const val MAX_PWD_LENGTH = 12
    }
}