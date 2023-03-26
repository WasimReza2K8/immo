package com.wasim.feature.realestate.presentation.viewmodel.detail

import com.example.core.state.Event
import com.example.core.viewmodel.ErrorEvent
import com.example.core.viewmodel.ViewEvent
import com.example.core.viewmodel.ViewState
import com.wasim.feature.realestate.presentation.model.PropertyDetailUiModel

internal object PropertyDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val property: PropertyDetailUiModel? = null,
        val errorUiEvent: Event<ErrorEvent>? = null,
    ) : ViewState

    sealed interface UiEvent : ViewEvent {
        data class OnViewModelInit(val id: Int) : UiEvent
        object OnBackButtonClicked : UiEvent
    }
}
