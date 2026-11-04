package com.kharisma.campusconnect.di

import com.kharisma.campusconnect.domain.GetCoursesUseCase
import com.kharisma.campusconnect.shared.CourseRepository
import com.kharisma.campusconnect.shared.CourseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideCourseRepository(): CourseRepository = CourseRepositoryImpl()

    @Provides
    fun provideGetCoursesUseCase(repo: CourseRepository): GetCoursesUseCase =
        GetCoursesUseCase(repo)
}
