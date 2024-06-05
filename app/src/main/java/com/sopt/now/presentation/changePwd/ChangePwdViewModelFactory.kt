package com.sopt.now.presentation.changePwd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.domain.FollowerRepository

class ChangePwdViewModelFactory(private val followerRepository: FollowerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePwdViewModel::class.java)) {
            return ChangePwdViewModel(followerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}