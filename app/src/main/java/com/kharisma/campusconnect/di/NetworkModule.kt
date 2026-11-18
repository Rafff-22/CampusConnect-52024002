package com.kharisma.campusconnect.di

import com.kharisma.campusconnect.data.remote.AnnouncementApi
import com.kharisma.campusconnect.data.repository.AnnouncementRepositoryImpl
import com.kharisma.campusconnect.domain.AnnouncementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAnnouncementApi(retrofit: Retrofit): AnnouncementApi =
        retrofit.create(AnnouncementApi::class.java)

    @Provides
    @Singleton
    fun provideAnnouncementRepository(api: AnnouncementApi): AnnouncementRepository =
        AnnouncementRepositoryImpl(api)
}
