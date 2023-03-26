/*
 * Copyright 2023 Wasim Reza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wasim.feature.realestate.di

import android.content.Context
import androidx.room.Room
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.PropertyDB
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.presentation.launcher.RealEstateFeatureImpl
import com.wasim.realestate.presentation.RealEstateFeature
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RealEstatePresentationModule {

    @Singleton
    @Binds
    fun bindFeature(feature: RealEstateFeatureImpl): RealEstateFeature
}

@Module
@InstallIn(SingletonComponent::class)
object RealEstateDataModule {
    private const val DATABASE_NAME = "property_db"

    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit): PropertyApi = retrofit.create(PropertyApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PropertyDB =
        Room.databaseBuilder(
            context.applicationContext,
            PropertyDB::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(database: PropertyDB): PropertyDao = database.propertyDao()
}
