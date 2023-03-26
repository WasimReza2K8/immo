/*
 * Copyright 2021 Wasim Reza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wasim.feature.realestate.data.repository

import android.database.sqlite.SQLiteException
import app.cash.turbine.test
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.data.datasource.db.entity.PropertyEntity
import com.wasim.feature.realestate.data.datasource.remote.dto.PropertyDto
import com.wasim.feature.realestate.domain.model.Property
import com.wasim.feature.realestate.utils.property
import com.wasim.feature.realestate.utils.propertyEntity
import com.wasim.feature.realestate.utils.propertyEntityList
import com.wasim.feature.realestate.utils.propertyItem
import com.wasim.feature.realestate.utils.propertyList
import com.wasim.feature.realestate.utils.propertyResponseList
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class PropertyRepositoryTest {
    private val api: PropertyApi = mockk(relaxed = true)
    private val dao: PropertyDao = mockk(relaxed = true)
    private val repository: PropertyRepository = PropertyRepository(
        api = api,
        dao = dao,
    )

    private fun mockApi(
        result: PropertyDto? = null,
        error: Throwable? = null,
        isSinglePropertyAvailable: Boolean = false,
    ) = runTest {
        if (error == null && result != null) {
            coEvery {
                api.fetchProperties()
            } returns result

        } else if (isSinglePropertyAvailable) {
            coEvery {
                api.fetchProperty(any())
            } returns propertyItem
        } else if (error != null) {
            coEvery {
                api.fetchProperties()
            } throws error
            coEvery {
                api.fetchProperty(any())
            } throws error
        }
    }

    private fun mockDao(
        result: List<PropertyEntity>? = null,
        error: Throwable? = null,
        isSinglePropertyAvailable: Boolean = false,
    ) = runTest {
        if (error == null && result != null) {
            coEvery {
                dao.getEntityList()
            } returns result

        } else if (isSinglePropertyAvailable) {
            coEvery {
                dao.getEntityById(any())
            } returns propertyEntity
        } else if (error != null) {
            coEvery {
                dao.getEntityList()
            } throws error
            coEvery {
                dao.getEntityById(any())
            } throws error
        }
    }

    @Test
    fun `Given empty list in db and valid list from api, When getProperties called, Then returns valid list of properties`() =
        runTest {
            val given = propertyResponseList
            val expected = propertyList

            mockApi(given)
            mockDao(emptyList())

            repository.getProperties().test {
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `Given empty list from api and valid list from db, When getProperties called, Then returns valid list of properties`() =
        runTest {
            val given = propertyEntityList
            val expected = propertyList

            mockApi(PropertyDto(emptyList()))
            mockDao(given)

            repository.getProperties().test {
                assertEquals(expected, awaitItem())
                awaitItem()
                awaitComplete()
            }
        }

    @Test
    fun `Given empty list from api and db, When getProperties called, Then returns empty list of properties`() =
        runTest {
            val expected = emptyList<Property>()

            mockApi(PropertyDto(emptyList()))
            mockDao(emptyList())

            repository.getProperties().test {
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `Given valid list from api and db, When getProperties called, Then returns valid list of properties`() =
        runTest {
            val expected = propertyList

            mockApi(propertyResponseList)
            mockDao(propertyEntityList)

            repository.getProperties().test {
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `Given error from api, When getProperties called, Then throw exception`() =
        runTest {
            val given = IOException()

            mockApi(error = given, result = null)
            mockDao(propertyEntityList)

            repository.getProperties().test {
                awaitItem()
                assertEquals(given, awaitError())
            }
        }

    @Test
    fun `Given error from db, When getProperties called, Then throw exception`() =
        runTest {
            val given = SQLiteException()

            mockApi(result = propertyResponseList)
            mockDao(error = given)

            repository.getProperties().test {
                assertEquals(given, awaitError())
            }
        }

    @Test
    fun `Given no entity in db and valid item from api, When getProperty called, Then returns valid property`() =
        runTest {
            val expected = property

            mockApi(isSinglePropertyAvailable = true)

            repository.getProperty(1).test {
                awaitItem()
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `Given error from api and valid item from db, When getProperty called, Then returns valid property`() =
        runTest {
            val expected = property

            mockApi(error = IOException(), result = null)
            mockDao(isSinglePropertyAvailable = true)

            repository.getProperty(1).test {
                assertEquals(expected, awaitItem())
                awaitError()
            }
        }

    @Test
    fun `Given error from api and db, When getProperty called, Then throw exception`() =
        runTest {
            val expected = SQLiteException()

            mockApi(error = IOException())
            mockDao(error = expected)

            repository.getProperty(1).test {
                assertEquals(expected, awaitError())
            }
        }

    @Test
    fun `Given valid item from api and db, When getProperty called, Then returns valid property`() =
        runTest {
            val expected = property

            mockApi(isSinglePropertyAvailable = true)
            mockDao(isSinglePropertyAvailable = true)

            repository.getProperty(1).test {
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `Given IOException from api, When getProperty called, Then throw exception`() =
        runTest {
            val given = IOException()

            mockApi(error = given, result = null)
            mockDao(isSinglePropertyAvailable = true)

            repository.getProperty(1).test {
                awaitItem()
                assertEquals(given, awaitError())
            }
        }

    @Test
    fun `Given SQLiteException from db, When getProperty called, Then throw exception`() =
        runTest {
            val given = SQLiteException()

            mockApi(isSinglePropertyAvailable = true)
            mockDao(error = given)

            repository.getProperty(1).test {
                assertEquals(given, awaitError())
            }
        }
}