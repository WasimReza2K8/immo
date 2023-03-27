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

package com.wasim.feature.realestate.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.core.navigation.Navigator
import com.example.core.resProvider.ResourceProvider
import com.example.core.state.Output
import com.example.core.state.Output.NetworkError
import com.example.core.state.Output.Success
import com.example.core.ui.R.string
import com.example.core.viewmodel.ErrorEvent
import com.example.core.viewmodel.ErrorEvent.UnknownError
import com.wasim.feature.realestate.domain.usecase.GetPropertyUseCase
import com.wasim.feature.realestate.presentation.launcher.PropertyDetailLauncher
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent.OnBackButtonClicked
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailViewModel
import com.wasim.feature.realestate.utils.property
import com.wasim.feature.realestate.utils.propertyDetail
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PropertyDetailViewModelTest : BaseViewModelTest() {
    private val getPropertyUseCase: GetPropertyUseCase = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val navigator: Navigator = mockk(relaxed = true)

    companion object {
        private const val NETWORK_ERROR = "network error"
        private const val UNKNOWN_ERROR = "unknown error"
    }

    private val resourceProvider: ResourceProvider = mockk {
        every {
            getString(string.network_error)
        } returns NETWORK_ERROR
        every {
            getString(string.unknown_error)
        } returns UNKNOWN_ERROR
    }
    private lateinit var viewModel: PropertyDetailViewModel

    private fun createViewModel() {
        viewModel = PropertyDetailViewModel(
            useCase = getPropertyUseCase,
            resourceProvider = resourceProvider,
            savedStateHandle = savedStateHandle,
            navigator = navigator
        )
    }

    private fun mockSaveStateHandle(id: Int) {
        every {
            savedStateHandle.get<Int>(PropertyDetailLauncher.ID)
        } returns id
    }

    private fun mockUseCase(
        isValidOutput: Boolean = false,
        isNetworkError: Boolean = false,
    ) = runTest {
        if (isValidOutput) {
            coEvery {
                getPropertyUseCase.invoke(any())
            } returns flow { emit(Success(property)) }
        } else if (isNetworkError) {
            coEvery {
                getPropertyUseCase.invoke(any())
            } returns flow { emit(NetworkError) }
        } else {
            coEvery {
                getPropertyUseCase.invoke(any())
            } returns flow { emit(Output.UnknownError) }
        }

    }

    @Test
    fun `Given valid property, When getPropertyUseCase invoked, Then view state having property`() =
        runTest {
            mockUseCase(isValidOutput = true)
            mockSaveStateHandle(1)

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals(it.property, propertyDetail)
            }
        }

    @Test
    fun `Given network error from useCase, When getPropertyUseCase invoked, Then view state contains network error message`() =
        runTest {
            mockUseCase(isNetworkError = true)
            mockSaveStateHandle(1)

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals(
                    (it.errorUiEvent!!.getContentIfNotHandled() as ErrorEvent.NetworkError).message,
                    NETWORK_ERROR
                )
            }
        }

    @Test
    fun `Given unknown error from useCase, When getPropertyUseCase invoked, Then view state contains unknown error message`() =
        runTest {
            mockUseCase()
            mockSaveStateHandle(1)

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals((it.errorUiEvent!!.getContentIfNotHandled() as UnknownError).message, UNKNOWN_ERROR)
            }
        }

    @Test
    fun `When OnBackButtonClicked, Then navigateUp called`() {
        mockSaveStateHandle(1)

        createViewModel()
        viewModel.onUiEvent(OnBackButtonClicked)

        verify(exactly = 1) { navigator.navigateUp() }
    }

    @Test
    fun `Given id is -1, When init viewModel, Then getPropertyUseCase never called`() = runTest {
        mockSaveStateHandle(-1)
        createViewModel()
        verify(exactly = 0) { getPropertyUseCase.invoke(any()) }
    }
}
