package com.wasim.feature.realestate.presentation.view.list

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.core.ui.theme.WasimTheme
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.presentation.model.PropertyListUiModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PropertyList(
    properties: List<PropertyListUiModel>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = getPadding())
            .testTag(stringResource(R.string.realestate_property_list)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(WasimTheme.spacing.spacing12),
        contentPadding = PaddingValues(
            top = WasimTheme.spacing.spacing12,
            bottom = WasimTheme.spacing.spacing12
        )
    ) {
        items(items = properties, key = { it.id }) { item ->
            PropertyListItem(
                id = item.id,
                imageUrl = item.url,
                price = item.price,
                area = item.area.text,
                areaSuperScript = item.area.superScript,
                city = item.city,
                onClick = onItemClick,
                modifier = modifier.animateItemPlacement(),
            )
        }
    }
}

@Composable
private fun getPadding(): Dp {
    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .distinctUntilChanged()
            .collect { orientation = it }
    }

    return when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            WasimTheme.spacing.spacing80
        }
        else -> {
            WasimTheme.spacing.spacing0
        }
    }
}
