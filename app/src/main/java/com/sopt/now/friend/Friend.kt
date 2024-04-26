package com.sopt.now.friend

import androidx.annotation.DrawableRes

data class Friend(
    @DrawableRes val profileImage: Int,
    val name: String,
    val selfDescription: String,
)
