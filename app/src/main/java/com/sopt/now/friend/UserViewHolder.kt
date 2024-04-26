package com.sopt.now.friend

import UserData
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(userData: UserData) {
        binding.run {
            tvName.text = userData.nickname
            tvSelfDescription.text = "mbti는 ${userData.mbti}입니다."
        }
    }
}