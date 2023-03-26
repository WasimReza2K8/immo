package com.wasim.feature.realestate.data.datasource.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property_table")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "bedrooms")
    val bedrooms: Int?,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "area")
    val area: Double,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "professional")
    val professional: String,
    @ColumnInfo(name = "propertyType")
    val propertyType: String,
    @ColumnInfo(name = "offerType")
    val offerType: Int,
    @ColumnInfo(name = "rooms")
    val rooms: Int?,
)
