package com.sopt.now.data.di

import com.sopt.now.data.repositoryImpl.AuthRepositoryImpl
import com.sopt.now.data.repositoryImpl.FollowerRepositoryImpl
import com.sopt.now.data.service.AuthService
import com.sopt.now.data.service.FriendService
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.repository.AuthRepository
import com.sopt.now.domain.repository.FollowerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Singleton
    @Provides
    fun provideFollowerRepository(
        userService: UserService,
        friendService: FriendService
    ): FollowerRepository {
        return FollowerRepositoryImpl(userService, friendService)
    }
}