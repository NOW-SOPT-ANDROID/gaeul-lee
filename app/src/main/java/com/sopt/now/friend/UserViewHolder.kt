package com.sopt.now.friend

import com.sopt.now.model.UserData
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(userData: UserData) {
        binding.run {
            tvName.text = userData.nickname
            tvSelfDescription.text = userData.phone
        }
    }
}