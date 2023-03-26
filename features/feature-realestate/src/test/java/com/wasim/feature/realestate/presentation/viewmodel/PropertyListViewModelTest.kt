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

import com.example.core.navigation.Navigator
import com.example.core.resProvider.ResourceProvider
import com.example.core.state.Output.NetworkError
import com.example.core.state.Output.Success
import com.example.core.state.Output.UnknownError
import com.example.core.ui.R.string
import com.example.core.viewmodel.ErrorEvent
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.domain.usecase.GetAllPropertiesUseCase
import com.wasim.feature.realestate.presentation.launcher.PropertyDetailLauncher
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent.OnItemClicked
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListViewModel
import com.wasim.feature.realestate.utils.propertyList
import com.wasim.feature.realestate.utils.propertyUiList
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Test

class PropertyListViewModelTest : BaseViewModelTest() {
    private val getAllPropertiesUseCase: GetAllPropertiesUseCase = mockk(relaxed = true)
    private val detailLauncher: PropertyDetailLauncher = mockk(relaxed = true)
    private val navigator: Navigator = mockk(relaxed = true)

    companion object {
        private const val NETWORK_ERROR = "network error"
        private const val UNKNOWN_ERROR = "unknown error"
        private const val NO_PROPERTY = "no property"
    }

    private val resourceProvider: ResourceProvider = mockk {
        every {
            getString(string.network_error)
        } returns NETWORK_ERROR
        every {
            getString(string.unknown_error)
        } returns UNKNOWN_ERROR
        every {
            getString(R.string.realestate_no_property_found)
        } returns NO_PROPERTY
    }
    private lateinit var viewModel: PropertyListViewModel

    private fun createViewModel() {
        viewModel = PropertyListViewModel(
            getAllPropertiesUseCase = getAllPropertiesUseCase,
            resourceProvider = resourceProvider,
            detailLauncher = detailLauncher,
            navigator = navigator,
        )
    }

    private fun mockUseCase(
        isValidOutput: Boolean = false,
        isNetworkError: Boolean = false,
    ) = runTest {
        if (isValidOutput) {
            coEvery {
                getAllPropertiesUseCase()
            } returns flow { emit(Success(propertyList)) }
        } else if (isNetworkError) {
            coEvery {
                getAllPropertiesUseCase()
            } returns flow { emit(NetworkError) }
        } else {
            coEvery {
                getAllPropertiesUseCase()
            } returns flow { emit(UnknownError) }
        }

    }

    @Test
    fun `Given valid properties, When useCase invoked, Then returns success output with properties`() =
        runTest {
            val expected = propertyUiList
            mockUseCase(isValidOutput = true)

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals(it.properties, expected)
            }
        }

    @Test
    fun `Given valid output with empty restaurants, When OnStart called, Then state contains info text with no restaurants`() =
        runTest {
            coEvery {
                getAllPropertiesUseCase()
            } returns flow { emit(Success(emptyList())) }

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                Assertions.assertThat(it.infoText == NO_PROPERTY).isTrue
            }
        }

    @Test
    fun `Given unknown error while getting properties, When useCase invoked, Then returns success output with unknown error`() =
        runTest {
            mockUseCase()

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals(
                    (it.errorUiEvent!!.getContentIfNotHandled() as ErrorEvent.UnknownError).message,
                    UNKNOWN_ERROR
                )
            }
        }

    @Test
    fun `Given network error while getting properties, When useCase invoked, Then returns success output with network error`() =
        runTest {
            mockUseCase(isNetworkError = true)

            createViewModel()

            viewModel.viewState.take(1).collectLatest {
                assertEquals(
                    (it.errorUiEvent!!.getContentIfNotHandled() as ErrorEvent.NetworkError).message,
                    NETWORK_ERROR
                )
            }
        }

    @Test
    fun `When OnNoteClicked, Then navigate called`() = runTest {
        val id = 1
        createViewModel()
        viewModel.onUiEvent(OnItemClicked(id))

        verify(exactly = 1) { navigator.navigate(detailLauncher.route(1)) }
    }
}
