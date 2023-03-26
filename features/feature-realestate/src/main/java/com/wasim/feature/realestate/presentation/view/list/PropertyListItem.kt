package com.wasim.feature.realestate.presentation.view.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.R
import com.example.core.ui.theme.WasimTheme

@Composable
internal fun PropertyListItem(
    id: Int,
    imageUrl: String,
    area: String,
    areaSuperScript: String,
    price: String,
    city: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(WasimTheme.spacing.spacing200)
            .clickable {
                onClick(id)
            }
            .testTag(stringResource(id = R.string.property_item))
    ) {
        PhotoWithInfoView(
            price = price,
            area = area,
            city = city,
            imageUrl = imageUrl,
            areaSuperScript = areaSuperScript
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    WasimTheme {
        Column {
            PropertyListItem(
                id = 8734,
                imageUrl = "https://cdn.pixabay.com/photo/2013/10/15/09/12/flower-195893_150.jpg",
                area = "233.0",
                price = "2220202.0",
                city = "city",
                onClick = { _ -> },
                areaSuperScript = "2"
            )
        }
    }
}
