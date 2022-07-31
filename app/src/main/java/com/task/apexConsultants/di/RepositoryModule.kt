package com.task.apexConsultants.di

import com.task.apexConsultants.api.ApiService
import com.task.apexConsultants.repository.PostsRepository
import com.task.apexConsultants.repository.PostsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    internal fun providePostsRepository(
        @NetworkModule.DefaultRetrofit apiService: ApiService
    ) : PostsRepository =
        PostsRepositoryImpl(apiService)
}