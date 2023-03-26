package com.wasim.feature.realestate.domain.model

internal data class Property(
    val area: Double,
    val bedrooms: String,
    val city: String,
    val id: Int,
    val offerType: Int,
    val price: Double,
    val professional: String,
    val propertyType: String,
    val rooms: String,
    val url: String
)
