package com.task.apexConsultants.di

import com.task.apexConsultants.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @DefaultRetrofit
    @Provides
    @Singleton
    internal fun providePostsApiService(@DefaultRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultRetrofit
}