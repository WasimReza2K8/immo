package com.wasim.feature.realestate.presentation.viewmodel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.Navigator
import com.example.core.resProvider.ResourceProvider
import com.example.core.state.Event
import com.example.core.state.Output
import com.example.core.state.Output.Success
import com.example.core.state.Output.UnknownError
import com.example.core.ui.R.string
import com.example.core.viewmodel.BaseViewModel
import com.example.core.viewmodel.ErrorEvent
import com.example.core.viewmodel.ErrorEvent.NetworkError
import com.wasim.feature.realestate.domain.usecase.GetPropertyUseCase
import com.wasim.feature.realestate.presentation.launcher.PropertyDetailLauncher.Companion.ID
import com.wasim.feature.realestate.presentation.mapper.toPropertyDetailUiModel
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.State
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent.OnBackButtonClicked
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent.OnViewModelInit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PropertyDetailViewModel @Inject constructor(
    private val useCase: GetPropertyUseCase,
    private val navigator: Navigator,
    private val resourceProvider: ResourceProvider,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<UiEvent, State>() {
    override fun provideInitialState() = State()
    private val id: Int by lazy {
        savedStateHandle.get<Int>(ID) ?: -1
    }

    override fun handleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is OnViewModelInit -> getProperty(uiEvent.id)
            is OnBackButtonClicked -> navigator.navigateUp()
        }
    }

    private fun getProperty(id: Int) {
        if (id == -1) {
            handleUnknownError()
        } else {
            viewModelScope.launch {
                useCase(id).collect { output ->
                    when (output) {
                        is Success -> {
                            updateState { copy(property = output.result.toPropertyDetailUiModel(resourceProvider = resourceProvider)) }
                        }
                        is UnknownError -> {
                            handleUnknownError()
                        }
                        is Output.NetworkError -> {
                            updateState {
                                copy(
                                    errorUiEvent = Event(
                                        NetworkError(resourceProvider.getString(string.network_error))
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleUnknownError() {
        updateState {
            copy(
                errorUiEvent = Event(
                    ErrorEvent.UnknownError(resourceProvider.getString(string.unknown_error))
                )
            )
        }
    }

    init {
        onUiEvent(OnViewModelInit(id))
    }
}
