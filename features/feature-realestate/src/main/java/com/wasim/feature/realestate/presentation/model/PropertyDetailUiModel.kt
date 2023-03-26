package com.wasim.feature.realestate.presentation.model

internal data class PropertyDetailUiModel(
    val area: Area,
    val bedrooms: String,
    val city: String,
    val id: Int,
    val offerType: Int,
    val price: String,
    val professional: String,
    val propertyType: String,
    val rooms: String,
    val url: String,
)
