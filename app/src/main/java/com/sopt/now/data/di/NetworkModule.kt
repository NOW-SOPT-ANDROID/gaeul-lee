package com.sopt.now.data.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig.AUTH_BASE_URL
import com.sopt.now.BuildConfig.FRIEND_BASE_URL
import com.sopt.now.data.service.AuthService
import com.sopt.now.data.service.FriendService
import com.sopt.now.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }

    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLogOkHttpClient())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder): AuthService {
        return retrofit.baseUrl(AUTH_BASE_URL).build().create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit.Builder): UserService {
        return retrofit.baseUrl(AUTH_BASE_URL).build().create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideFriendService(retrofit: Retrofit.Builder): FriendService {
        return retrofit.baseUrl(FRIEND_BASE_URL).build().create(FriendService::class.java)
    }
}