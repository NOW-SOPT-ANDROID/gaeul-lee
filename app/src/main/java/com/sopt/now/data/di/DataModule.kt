package com.sopt.now.data.di

import com.sopt.now.data.datasource.AuthDataSource
import com.sopt.now.data.datasource.FriendDataSource
import com.sopt.now.data.datasource.UserDataSource
import com.sopt.now.data.datasourceImpl.AuthDataSourceImpl
import com.sopt.now.data.datasourceImpl.FriendDataSourceImpl
import com.sopt.now.data.datasourceImpl.UserDataSourceImpl
import com.sopt.now.data.repositoryImpl.AuthRepositoryImpl
import com.sopt.now.data.repositoryImpl.FriendRepositoryImpl
import com.sopt.now.data.repositoryImpl.UserRepositoryImpl
import com.sopt.now.data.service.AuthService
import com.sopt.now.data.service.FriendService
import com.sopt.now.data.service.UserService
import com.sopt.now.domain.repository.AuthRepository
import com.sopt.now.domain.repository.FriendRepository
import com.sopt.now.domain.repository.UserRepository
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
    fun provideAuthDataSource(
        authService: AuthService
    ): AuthDataSource {
        return AuthDataSourceImpl(authService)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }
    @Singleton
    @Provides
    fun provideUserDataSource(
        userService: UserService
    ): UserDataSource {
        return UserDataSourceImpl(userService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }

    @Singleton
    @Provides
    fun provideFriendDataSource(
        friendService: FriendService
    ): FriendDataSource {
        return FriendDataSourceImpl(friendService)
    }

    @Singleton
    @Provides
    fun provideFriendRepository(
        friendDataSource: FriendDataSource
    ): FriendRepository {
        return FriendRepositoryImpl(friendDataSource)
    }
}