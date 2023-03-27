package com.example.wasim.realestate.data

import com.example.wasim.utils.ReturnType
import com.example.wasim.utils.ReturnType.UnknownException
import com.example.wasim.utils.ReturnType.Valid
import com.example.wasim.utils.ReturnType.ValidEmptyList
import com.example.wasim.utils.propertyEntity
import com.example.wasim.utils.propertyEntityList
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity

class FakePropertyDao : PropertyDao {
    var returnType: ReturnType = Valid

    override suspend fun insertEntity(entity: PropertyEntity): Long =
        when (returnType) {
            Valid -> 1L
            UnknownException -> -1L
            else -> {
                throw RuntimeException("Wrong input for test")
            }
        }

    override suspend fun insertEntities(entities: List<PropertyEntity>): List<Long> =
        when (returnType) {
            Valid -> listOf(1L)
            UnknownException -> listOf(-1L)
            else -> {
                throw RuntimeException("Wrong input for test")
            }
        }

    override suspend fun getEntityById(id: Int): PropertyEntity? =
        when (returnType) {
            Valid -> propertyEntity
            ValidEmptyList -> null
            else -> {
                throw RuntimeException("Wrong input for test")
            }
        }

    override suspend fun getEntityList(): List<PropertyEntity> =
        when (returnType) {
            Valid -> propertyEntityList
            ValidEmptyList -> emptyList()
            else -> {
                throw RuntimeException("Wrong input for test")
            }
        }
}