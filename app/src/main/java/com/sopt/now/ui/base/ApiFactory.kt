package com.sopt.now.ui.base

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig.AUTH_BASE_URL
import com.sopt.now.BuildConfig.FRIEND_BASE_URL
import com.sopt.now.remote.service.AuthService
import com.sopt.now.remote.service.FriendService
import com.sopt.now.remote.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    private const val CONTENT_TYPE = "application/json"

    fun retrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(Json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }

    inline fun <reified T> create(url: String): T = retrofit(url).create(T::class.java)
}

object ServicePool {
    val userService by lazy { ApiFactory.create<UserService>(AUTH_BASE_URL) }
    val authService by lazy { ApiFactory.create<AuthService>(AUTH_BASE_URL) }
    val friendService by lazy { ApiFactory.create<FriendService>(FRIEND_BASE_URL) }
}