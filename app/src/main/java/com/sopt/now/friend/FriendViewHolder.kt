package com.sopt.now.friend

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopt.now.R
import com.sopt.now.databinding.ItemFriendBinding

class FriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(friendData: Friend) {
        binding.run {
            Glide.with(ivProfile.context)
                .load(friendData.profileImage)
                .error(R.drawable.ic_pets_pink_24)
                .into(ivProfile)
            tvName.text = friendData.name
            tvSelfDescription.text = friendData.selfDescription
        }
    }
}