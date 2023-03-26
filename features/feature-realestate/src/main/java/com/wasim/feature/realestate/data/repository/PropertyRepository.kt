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

package com.wasim.feature.realestate.data.repository

import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import com.wasim.feature.realestate.data.mapper.toProperty
import com.wasim.feature.realestate.data.mapper.toPropertyEntity
import com.wasim.feature.realestate.domain.model.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class PropertyRepository @Inject constructor(
    private val api: PropertyApi,
    private val dao: PropertyDao,
) {

    fun getProperties(): Flow<List<Property>> =
        flow {
            emit(fetchProperties())
        }.onStart {
            val properties = getLocalProperties()
            if (properties.isNotEmpty()) {
                emit(
                    properties.map { it.toProperty() }
                )
            }
        }.distinctUntilChanged()

    fun getProperty(id: Int): Flow<Property> =
        flow {
            emit(fetchProperty(id))
        }.onStart {
            val property = getLocalPropertyById(id)
            if (property != null) {
                emit(property)
            }
        }.distinctUntilChanged()

    private suspend fun fetchProperty(id: Int): Property {
        val response = api.fetchProperty(id)
        dao.insertEntity(response.toPropertyEntity())
        return response.toProperty()
    }

    private suspend fun fetchProperties(): List<Property> {
        val response = api.fetchProperties()
        val entities = response.items.map { item ->
            item.toPropertyEntity()
        }
        dao.insertEntities(entities)
        return response.items.map { it.toProperty() }
    }

    private suspend fun getLocalProperties(): List<PropertyEntity> = dao.getEntityList()

    private suspend fun getLocalPropertyById(id: Int): Property? = dao.getEntityById(id)?.toProperty()
}
