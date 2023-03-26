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

package com.wasim.feature.realestate.domain.usecase

import app.cash.turbine.test
import com.example.core.dispatcher.BaseDispatcherProvider
import com.example.core.state.Output.NetworkError
import com.example.core.state.Output.Success
import com.example.core.state.Output.UnknownError
import com.wasim.feature.realestate.data.repository.PropertyRepository
import com.wasim.feature.realestate.utils.TestDispatcherProvider
import com.wasim.feature.realestate.utils.propertyList
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class GetAllPropertiesUseCaseTest {
    private val repository: PropertyRepository = mockk(relaxed = true)
    private val dispatcherProvider: BaseDispatcherProvider = TestDispatcherProvider()
    private val useCase: GetAllPropertiesUseCase = GetAllPropertiesUseCase(
        repository = repository,
        dispatcherProvider = dispatcherProvider
    )

    private fun mockRepository(
        error: Throwable? = null,
    ) = runTest {
        if (error != null) {
            coEvery {
                repository.getProperties()
            } returns flow { throw error }
        } else {
            coEvery {
                repository.getProperties()
            } returns flow { emit(propertyList) }
        }
    }

    @Test
    fun `Given valid properties, When useCase invoked, Then emits valid list of properties`() =
        runTest {
            mockRepository()

            useCase().test {
                assertEquals(propertyList, (awaitItem() as Success).result)
                awaitComplete()
            }
        }

    @Test
    fun `Given IoException from repository, When useCase invoked, Then emits networkError`() =
        runTest {
            mockRepository(error = IOException())

            useCase().test {
                assertEquals(awaitItem(), NetworkError)
                awaitComplete()
            }
        }

    @Test
    fun `Given RuntimeException from repository, When useCase invoked, Then emits unknownError`() =
        runTest {
            mockRepository(error = RuntimeException())

            useCase().test {
                assertEquals(awaitItem(), UnknownError)
                awaitComplete()
            }
        }
}
