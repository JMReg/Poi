package com.jmr.poi.di

import android.app.Application
import com.jmr.poi.data.api.ApiService
import com.jmr.poi.data.repository.poi.PoiRepository
import com.jmr.poi.data.repository.poi.PoiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePoiRepository(
        application: Application,
        apiService: ApiService
    ): PoiRepository = PoiRepositoryImpl(application, apiService)

}