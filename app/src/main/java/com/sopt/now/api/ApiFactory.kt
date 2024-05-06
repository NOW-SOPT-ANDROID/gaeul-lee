package com.sopt.now.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import com.sopt.now.BuildConfig.AUTH_BASE_URL
import com.sopt.now.BuildConfig.FRIEND_BASE_URL
import com.sopt.now.service.AuthService
import com.sopt.now.service.FriendService
import com.sopt.now.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object ApiFactory {
    private const val CONTENT_TYPE = "application/json"
    private const val AUTH_BASE_URL: String = BuildConfig.AUTH_BASE_URL
    private const val FRIEND_BASE_URL: String = BuildConfig.FRIEND_BASE_URL

    fun retrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(Json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }

    inline fun <reified T> create(url: String): T = retrofit(url).create(T::class.java)
}

object ServicePool {
    val userService = ApiFactory.create<UserService>(AUTH_BASE_URL)
    val authService = ApiFactory.create<AuthService>(AUTH_BASE_URL)
    val friendService = ApiFactory.create<FriendService>(FRIEND_BASE_URL)
}