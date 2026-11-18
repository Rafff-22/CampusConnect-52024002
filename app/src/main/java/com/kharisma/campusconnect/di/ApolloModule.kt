package com.kharisma.campusconnect.di

import com.apollographql.apollo.ApolloClient
import com.kharisma.campusconnect.data.repository.InfoMitraRepositoryImpl
import com.kharisma.campusconnect.domain.InfoMitraRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient = ApolloClient.Builder()
        .serverUrl("https://countries.trevorblades.com/")
        .build()

    @Provides
    @Singleton
    fun provideInfoMitraRepository(apolloClient: ApolloClient): InfoMitraRepository =
        InfoMitraRepositoryImpl(apolloClient)
}
