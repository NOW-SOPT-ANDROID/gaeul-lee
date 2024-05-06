package com.sopt.now.friend

import com.sopt.now.model.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemUserBinding

class FriendAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var friendList: List<Friend> = emptyList()
    private var user = UserData("", "", "")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_HEADER -> { // 맨 처음은 유저 프로필
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                return UserViewHolder(binding)
            }

            else -> {
                val binding = ItemFriendBinding.inflate(inflater, parent, false)
                return FriendViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendViewHolder) {
            holder.onBind(friendList[position])
        } else if (holder is UserViewHolder) {
            holder.onBind(user)
        }
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    fun setFriendList(friendList: List<Friend>) {
        this.friendList = friendList.toList()
        notifyDataSetChanged()
    }

    fun setUser(userData: UserData) {
        user = userData
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }
}