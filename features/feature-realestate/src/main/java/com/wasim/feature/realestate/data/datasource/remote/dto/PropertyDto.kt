package com.wasim.feature.realestate.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyDto(
    @SerialName("items")
    val items: List<ItemDto>,
)
