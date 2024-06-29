package com.sopt.now.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(user: User) {
        binding.run {
            tvName.text = user.nickname
            tvSelfDescription.text = user.phone
        }
    }
}