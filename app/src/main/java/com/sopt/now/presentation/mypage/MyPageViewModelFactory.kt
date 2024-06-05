package com.sopt.now.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.domain.FollowerRepository

class MyPageViewModelFactory(private val followerRepository: FollowerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            return MyPageViewModel(followerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}