package com.chelsea.nutrilog.core.di

import android.content.Context
import com.chelsea.nutrilog.core.api.ApiClient
import com.chelsea.nutrilog.core.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    
    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return ApiClient.getApiService(context)
    }
}
