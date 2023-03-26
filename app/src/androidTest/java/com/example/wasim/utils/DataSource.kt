package com.example.wasim.utils

import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import com.wasim.feature.realestate.data.datasource.remote.dto.ItemDto
import com.wasim.feature.realestate.data.datasource.remote.dto.PropertyDto

val propertyItem = ItemDto(
    area = 600.0,
    bedrooms = 3,
    city = "test",
    id = 1,
    offerType = 1,
    price = 340000.0,
    professional = "prof",
    propertyType = "apartment",
    rooms = 7,
    url = "test url",
)

val propertyEntity = PropertyEntity(
    area = 600.0,
    bedrooms = 3,
    city = "test",
    id = 1,
    offerType = 1,
    price = 340000.0,
    professional = "prof",
    propertyType = "apartment",
    rooms = 7,
    url = "test url",
)

val propertyEntityList = listOf(
    propertyEntity,
    propertyEntity.copy(id = 2),
    propertyEntity.copy(id = 3),
)

val propertyResponseList = PropertyDto(
    items = listOf(
        propertyItem,
        propertyItem.copy(id = 2),
        propertyItem.copy(id = 3),
    )
)
