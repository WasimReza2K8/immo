package com.wasim.feature.realestate.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.wasim.feature.realestate.data.datasource.db.PropertyDB
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyDBTest {
    private lateinit var db: PropertyDB
    private lateinit var context: Context
    private lateinit var dao: PropertyDao

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PropertyDB::class.java).build()
        dao = db.propertyDao()
    }

    @After
    fun clear() {
        db.clearAllTables()
        db.close()
    }

    private fun getPropertyEntity() = PropertyEntity(
        area = 600.0,
        bedrooms = 3,
        city = "test",
        id = 1,
        offerType = 1,
        price = 340000.0,
        professional = "prof",
        propertyType = "apartment",
        rooms = 7,
        url = "test url",
    )

    private fun getPropertyEntities() = listOf(
        getPropertyEntity()
    )

    @Test
    fun test_insertEntity() = runTest {
        val output = dao.insertEntity(getPropertyEntity())
        assertThat(output > 0).isTrue
    }

    @Test
    fun test_insertEntities() = runTest {
        val output = dao.insertEntities(getPropertyEntities())
        assertThat(output[0] > 0).isTrue
    }

     @Test
     fun test_getEntities_return_valid_entities() = runTest {
         dao.insertEntity(getPropertyEntity())
         val output = dao.getEntityList()
         assertThat(output.size == 1).isTrue
     }

     @Test
     fun test_getEntities_return_empty() = runTest {
         val output = dao.getEntityList()
         assertThat(output.isEmpty()).isTrue
     }

    @Test
    fun test_getEntity_return_valid_entity() = runTest {
        dao.insertEntity(getPropertyEntity())
        val output = dao.getEntityById(1)
        assertThat(output == getPropertyEntity()).isTrue
    }

    @Test
    fun test_getEntity_return_null_entity() = runTest {
        val output = dao.getEntityById(1)
        assertThat(output == null).isTrue
    }
}
