package com.sopt.now.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.domain.FollowerRepository


class HomeViewModelFactory(private val followerRepository: FollowerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(followerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
