package com.wasim.feature.realestate.data.mapper

import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import com.wasim.feature.realestate.data.datasource.remote.dto.ItemDto
import com.wasim.feature.realestate.domain.model.Property

internal fun ItemDto.toProperty(): Property =
    Property(
        id = id,
        url = url.orEmpty(),
        area = area,
        bedrooms = bedrooms?.toString().orEmpty(),
        city = city,
        offerType = offerType,
        price = price,
        professional = professional,
        propertyType = propertyType,
        rooms = rooms?.toString().orEmpty(),
    )

internal fun ItemDto.toPropertyEntity(): PropertyEntity =
    PropertyEntity(
        id = id,
        url = url,
        area = area,
        bedrooms = bedrooms,
        city = city,
        offerType = offerType,
        price = price,
        professional = professional,
        propertyType = propertyType,
        rooms = rooms,
    )

internal fun PropertyEntity.toProperty(): Property =
    Property(
        id = id,
        url = url.orEmpty(),
        area = area,
        bedrooms = bedrooms?.toString().orEmpty(),
        city = city,
        offerType = offerType,
        price = price,
        professional = professional,
        propertyType = propertyType,
        rooms = rooms?.toString().orEmpty(),
    )
