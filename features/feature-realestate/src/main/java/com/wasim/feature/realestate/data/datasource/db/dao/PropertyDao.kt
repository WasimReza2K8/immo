package com.wasim.feature.realestate.data.datasource.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(entity: PropertyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntities(entities: List<PropertyEntity>): List<Long>

    @Query("SELECT * FROM property_table where id = :id")
    suspend fun getEntityById(id: Int): PropertyEntity?

    @Query("SELECT * FROM property_table")
    suspend fun getEntityList(): List<PropertyEntity>
}
