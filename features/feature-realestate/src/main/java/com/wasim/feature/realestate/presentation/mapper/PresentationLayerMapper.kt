package com.wasim.feature.realestate.presentation.mapper

import com.example.core.resProvider.ResourceProvider
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.domain.model.Property
import com.wasim.feature.realestate.presentation.model.Area
import com.wasim.feature.realestate.presentation.model.PropertyDetailUiModel
import com.wasim.feature.realestate.presentation.model.PropertyListUiModel

internal fun Property.toPropertyDetailUiModel(resourceProvider: ResourceProvider): PropertyDetailUiModel =
    PropertyDetailUiModel(
        id = id,
        url = url,
        area = getArea(area),
        bedrooms = bedrooms.ifBlank {
            resourceProvider.getString(R.string.realestate_unknown)
        },
        city = city,
        offerType = offerType,
        price = getPrice(price),
        professional = professional,
        propertyType = propertyType,
        rooms = rooms.ifBlank {
            resourceProvider.getString(R.string.realestate_unknown)
        },
    )

internal fun Property.toPropertyListUiModel(): PropertyListUiModel =
    PropertyListUiModel(
        id = id,
        url = url,
        area = getArea(area),
        city = city,
        price = getPrice(price),
    )

private fun getPrice(price: Double) = "$price €"

private fun getArea(area: Double) = Area(
    text = "$area m",
    superScript = "2",
)
