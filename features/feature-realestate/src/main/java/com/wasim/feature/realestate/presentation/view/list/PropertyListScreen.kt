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

package com.wasim.feature.realestate.presentation.view.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.WasimTheme
import com.example.core.ui.views.ErrorSnakeBar
import com.example.core.ui.views.TopBar
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.presentation.model.Area
import com.wasim.feature.realestate.presentation.model.PropertyListUiModel
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.State
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListContract.UiEvent.OnItemClicked
import com.wasim.feature.realestate.presentation.viewmodel.list.PropertyListViewModel

@Composable
internal fun PropertyListScreen(viewModel: PropertyListViewModel = hiltViewModel()) {
    val state: State by viewModel.viewState.collectAsStateWithLifecycle()
    PropertyListScreenImpl(
        state = state,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
private fun PropertyListScreenImpl(
    state: State,
    sendEvent: (uiEvent: UiEvent) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    state.errorUiEvent?.getContentIfNotHandled()?.let { errorEvent ->
        ErrorSnakeBar(
            errorEvent = errorEvent,
            snackBarHostState = snackBarHostState,
        )
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
        topBar = {
            TopBar(title = stringResource(id = R.string.realestate_title))
        },
    ) { scaffoldPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = WasimTheme.spacing.spacing16,
                    top = scaffoldPadding.calculateTopPadding(),
                    end = WasimTheme.spacing.spacing16,
                    bottom = scaffoldPadding.calculateBottomPadding(),
                )
        ) {
            if (state.properties.isEmpty() && state.infoText.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.infoText)
                }
            } else {
                PropertyList(
                    modifier = Modifier
                        .fillMaxSize(),
                    properties = state.properties,
                    onItemClick = { id ->
                        sendEvent(OnItemClicked(id))
                    }
                )
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
private fun PropertyScreenPreview() {
    WasimTheme {
        PropertyListScreenImpl(
            state = State(
                properties = listOf(
                    PropertyListUiModel(
                        area = Area("300.0 m", "2"),
                        city = "city",
                        price = "price",
                        url = "url",
                        id = 1
                    )
                )
            ),
            sendEvent = {}
        )
    }
}
