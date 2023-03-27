package com.example.wasim.di

import com.example.wasim.realestate.data.FakePropertyDao
import com.example.wasim.realestate.data.FakePropertyApi
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeRealEstateDataModule {
    @Provides
    @Singleton
    fun providesApi(): PropertyApi = FakePropertyApi()

    @Singleton
    @Provides
    fun provideDao(): PropertyDao = FakePropertyDao()
}
