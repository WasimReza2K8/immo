package com.example.wasim.realestate.data

import com.example.wasim.utils.ReturnType
import com.example.wasim.utils.ReturnType.NetworkException
import com.example.wasim.utils.ReturnType.UnknownException
import com.example.wasim.utils.ReturnType.Valid
import com.example.wasim.utils.ReturnType.ValidEmptyList
import com.example.wasim.utils.propertyItem
import com.example.wasim.utils.propertyResponseList
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.remote.dto.ItemDto
import com.wasim.feature.realestate.data.datasource.remote.dto.PropertyDto
import java.io.IOException

class FakePropertyApi : PropertyApi {
    var returnType: ReturnType = Valid

    override suspend fun fetchProperties(): PropertyDto =
        when (returnType) {
            Valid -> propertyResponseList
            ValidEmptyList -> PropertyDto(emptyList())
            NetworkException -> throw IOException()
            UnknownException -> throw RuntimeException()
        }

    override suspend fun fetchProperty(id: Int): ItemDto =
        when (returnType) {
            Valid, ValidEmptyList -> propertyItem
            NetworkException -> throw IOException()
            UnknownException -> throw RuntimeException()
        }
}