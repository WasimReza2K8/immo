package com.wasim.feature.realestate.utils

import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import com.wasim.feature.realestate.data.datasource.remote.dto.ItemDto
import com.wasim.feature.realestate.data.datasource.remote.dto.PropertyDto
import com.wasim.feature.realestate.domain.model.Property
import com.wasim.feature.realestate.presentation.model.Area
import com.wasim.feature.realestate.presentation.model.PropertyDetailUiModel
import com.wasim.feature.realestate.presentation.model.PropertyListUiModel

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

internal val property = Property(
    area = 600.0,
    bedrooms = "3",
    city = "test",
    id = 1,
    offerType = 1,
    price = 340000.0,
    professional = "prof",
    propertyType = "apartment",
    rooms = "7",
    url = "test url",
)

internal val propertyList = listOf(
    property,
    property.copy(id = 2),
    property.copy(id = 3),
)

internal val propertyUiModel = PropertyListUiModel(
    area = Area("600.0 m", superScript = "2"),
    city = "test",
    id = 1,
    price = "340000.0 €",
    url = "test url",
)

internal val propertyUiList = listOf(
    propertyUiModel,
    propertyUiModel.copy(id = 2),
    propertyUiModel.copy(id = 3),
)

internal val propertyDetail = PropertyDetailUiModel(
    area = Area("600.0 m", superScript = "2"),
    bedrooms = "3",
    city = "test",
    id = 1,
    offerType = 1,
    price = "340000.0 €",
    professional = "prof",
    propertyType = "apartment",
    rooms = "7",
    url = "test url",
)

