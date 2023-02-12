package com.jmr.poi.di

import com.jmr.poi.data.api.model.poi.PoiModel
import com.jmr.poi.data.api.model.poi.PoiModelImpl
import com.jmr.poi.data.repository.poi.PoiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

    @Singleton
    @Provides
    fun providePoiModel(
        repository: PoiRepository
    ): PoiModel = PoiModelImpl(repository)
}