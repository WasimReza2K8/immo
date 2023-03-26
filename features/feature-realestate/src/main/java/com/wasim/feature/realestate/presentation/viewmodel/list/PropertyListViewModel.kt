/*
 * Copyright 2023 Wasim Reza.
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

package com.wasim.feature.realestate.presentation.viewmodel.list

import androidx.lifecycle.viewModelScope
import com.example.core.navigation.Navigator
import com.example.core.resProvider.ResourceProvider
import com.example.core.state.Event
import com.example.core.state.Output.NetworkError
import com.example.core.state.Output.Success
import com.example.core.state.Output.UnknownError
import com.example.core.ui.R.string
import com.example.core.viewmodel.BaseViewModel
import com.example.core.viewmodel.ErrorEvent
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.domain.model.Property
import com.wasim.feature.realestate.domain.usecase.GetAllPropertiesUseCase
import com.wasim.feature.realestate.presentation.launcher.PropertyDetailLauncher
import com.wasim.feature.realestate.presentation.mapper.toPropertyListUiModel
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.State
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent.OnItemClicked
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent.OnViewModelInit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PropertyListViewModel @Inject constructor(
    private val getAllPropertiesUseCase: GetAllPropertiesUseCase,
    private val resourceProvider: ResourceProvider,
    private val detailLauncher: PropertyDetailLauncher,
    private val navigator: Navigator,
) : BaseViewModel<UiEvent, State>() {

    override fun provideInitialState() = State()

    override fun handleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            OnViewModelInit -> {
                getAllProperties()
            }
            is OnItemClicked -> {
                navigator.navigate(detailLauncher.route(uiEvent.id))
            }
        }
    }

    private fun getAllProperties() {
        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            getAllPropertiesUseCase().collect { output ->
                updateState { copy(isLoading = false) }
                when (output) {
                    is Success -> {
                        handleSuccess(output)
                    }
                    is UnknownError -> {
                        updateState {
                            copy(
                                errorUiEvent = Event(
                                    ErrorEvent.UnknownError(resourceProvider.getString(string.unknown_error))
                                )
                            )
                        }
                    }
                    is NetworkError -> {
                        updateState {
                            copy(
                                errorUiEvent = Event(
                                    ErrorEvent.NetworkError(resourceProvider.getString(string.network_error))
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccess(output: Success<List<Property>>) {
        if (output.result.isEmpty()) {
            updateState {
                copy(
                    infoText = resourceProvider.getString(R.string.realestate_no_property_found),
                    properties = emptyList()
                )
            }
        } else {
            updateState {
                copy(
                    infoText = "",
                    properties = output.result.map { it.toPropertyListUiModel() }
                )
            }
        }
    }

    init {
        onUiEvent(OnViewModelInit)
    }
}
