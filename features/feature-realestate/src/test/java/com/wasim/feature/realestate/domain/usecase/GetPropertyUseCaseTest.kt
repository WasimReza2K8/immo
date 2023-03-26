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
import com.wasim.feature.realestate.utils.property
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class GetPropertyUseCaseTest {
    private val repository: PropertyRepository = mockk(relaxed = true)
    private val dispatcherProvider: BaseDispatcherProvider = TestDispatcherProvider()
    private val useCase: GetPropertyUseCase = GetPropertyUseCase(
        repository = repository,
        dispatcherProvider = dispatcherProvider
    )

    private fun mockRepository(
        error: Throwable? = null,
    ) = runTest {
        if (error != null) {
            coEvery {
                repository.getProperty(any())
            } returns flow { throw error }
        } else {
            coEvery {
                repository.getProperty(any())
            } returns flow { emit(property) }
        }
    }

    @Test
    fun `Given valid property, When useCase invoked, Then emits valid property`() =
        runTest {
            mockRepository()

            useCase(1).test {
                assertEquals(property, (awaitItem() as Success).result)
                awaitComplete()
            }
        }

    @Test
    fun `Given IoException from repository, When useCase invoked, Then emits networkError`() =
        runTest {
            mockRepository(error = IOException())

            useCase(1).test {
                assertEquals(awaitItem(), NetworkError)
                awaitComplete()
            }
        }

    @Test
    fun `Given RuntimeException from repository, When useCase invoked, Then emits unknownError`() =
        runTest {
            mockRepository(error = RuntimeException())

            useCase(1).test {
                assertEquals(awaitItem(), UnknownError)
                awaitComplete()
            }
        }
}
