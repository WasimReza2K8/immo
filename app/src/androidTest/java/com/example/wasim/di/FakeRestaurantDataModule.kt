package com.example.wasim.di

import com.example.wasim.restaurant.data.FakePropertyDao
import com.example.wasim.restaurant.data.FakePropertyApi
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeRestaurantDataModule {
    @Provides
    @Singleton
    fun providesApi(): PropertyApi = FakePropertyApi()

    @Singleton
    @Provides
    fun provideDao(): PropertyDao = FakePropertyDao()
}
