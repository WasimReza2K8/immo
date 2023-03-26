@file:Suppress("MagicNumber")
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

package com.wasim.feature.realestate.presentation.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.ui.theme.WasimTheme
import com.example.core.ui.views.ErrorSnakeBar
import com.example.core.ui.views.ImageView
import com.example.core.ui.views.TextWithSuperScript
import com.example.core.ui.views.TopBar
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.presentation.model.Area
import com.wasim.feature.realestate.presentation.model.PropertyDetailUiModel
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.State
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailContract.UiEvent.OnBackButtonClicked
import com.wasim.feature.realestate.presentation.viewmodel.detail.PropertyDetailViewModel

@Composable
internal fun PropertyDetailScreen(viewModel: PropertyDetailViewModel = hiltViewModel()) {
    val state: State by viewModel.viewState.collectAsStateWithLifecycle()
    PropertyDetailScreenImpl(
        state = state,
        sendEvent = { viewModel.onUiEvent(it) },
    )
}

@Composable
private fun PropertyDetailScreenImpl(
    state: State,
    sendEvent: (uiEvent: UiEvent) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    ErrorSnakeBar(
        errorEvent = state.errorUiEvent?.getContentIfNotHandled(),
        snackBarHostState = snackBarHostState,
    )

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.realestate_detail),
                onNavigateUp = { sendEvent(OnBackButtonClicked) }
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState())
                .testTag(stringResource(id = R.string.realestate_property_detail)),
        ) {

            state.property?.let {
                ImageView(
                    modifier = Modifier
                        .height(WasimTheme.spacing.spacing200)
                        .fillMaxWidth()
                        .padding(bottom = WasimTheme.spacing.spacing16),
                    url = state.property.url,
                    contentScale = ContentScale.Crop
                )
                PropertyDescription(state.property)
            }
        }
    }
}

@Composable
private fun PropertyDescription(property: PropertyDetailUiModel) {
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_price),
        property.price
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_area),
        property.area.text,
        superScript = property.area.superScript
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_city),
        property.city
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_rooms),
        property.rooms
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_bedrooms),
        property.bedrooms
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_propertyType),
        property.propertyType
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_offer_type),
        property.offerType.toString()
    )
    PropertyInfoText(
        stringResource(id = R.string.realestate_property_professional),
        property.professional
    )
}

@Composable
private fun PropertyInfoText(
    criteria: String,
    value: String,
    style: TextStyle = WasimTheme.typography.body2,
    superScript: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier
                .weight(0.5f)
                .wrapContentHeight()
                .padding(
                    horizontal = WasimTheme.spacing.spacing16
                ),
            text = criteria,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (superScript == null) {
            Text(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight()
                    .padding(
                        horizontal = WasimTheme.spacing.spacing16
                    ),
                text = value,
                style = style,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            TextWithSuperScript(
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight()
                    .padding(
                        horizontal = WasimTheme.spacing.spacing16
                    ),
                text = value,
                style = style,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                superScript = superScript,
            )
        }
    }
}

@Preview
@Composable
private fun PropertyDetailPreview() {
    WasimTheme {
        PropertyDetailScreenImpl(
            State(
                property = PropertyDetailUiModel(
                    area = Area("30000.0 m", "2"),
                    bedrooms = "bed rooms",
                    city = "city",
                    id = 1,
                    offerType = 3,
                    price = "435354.0",
                    professional = "professional",
                    propertyType = "propertyType",
                    rooms = "rooms",
                    url = "judsbfv",
                )
            )
        ) { }
    }
}
