package com.wasim.feature.realestate.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    @SerialName("area")
    val area: Double,
    @SerialName("bedrooms")
    val bedrooms: Int? = null,
    @SerialName("city")
    val city: String,
    @SerialName("id")
    val id: Int,
    @SerialName("offerType")
    val offerType: Int,
    @SerialName("price")
    val price: Double,
    @SerialName("professional")
    val professional: String,
    @SerialName("propertyType")
    val propertyType: String,
    @SerialName("rooms")
    val rooms: Int? = null,
    @SerialName("url")
    val url: String? = null,
)
