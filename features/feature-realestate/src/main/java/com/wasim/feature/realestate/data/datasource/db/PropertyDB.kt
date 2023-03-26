package com.wasim.feature.realestate.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase

import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity

@Database(
    entities = [PropertyEntity::class],
    version = 1,
    exportSchema = false,
)

abstract class PropertyDB : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}
