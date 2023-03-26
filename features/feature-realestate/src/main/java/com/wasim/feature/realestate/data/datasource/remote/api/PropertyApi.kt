package com.wasim.feature.realestate.data.datasource.remote.api

import com.wasim.feature.realestate.data.datasource.remote.dto.ItemDto
import com.wasim.feature.realestate.data.datasource.remote.dto.PropertyDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyApi {
    @GET("/listings.json")
    suspend fun fetchProperties(): PropertyDto

    @GET("/listings/{id}.json")
    suspend fun fetchProperty(@Path(value = "id") id: Int): ItemDto
}
