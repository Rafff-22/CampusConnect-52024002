package com.kharisma.campusconnect.di

import com.kharisma.campusconnect.data.NotificationSocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {
    @Provides
    @Singleton
    fun provideNotificationSocketManager(): NotificationSocketManager =
        NotificationSocketManager()
}
