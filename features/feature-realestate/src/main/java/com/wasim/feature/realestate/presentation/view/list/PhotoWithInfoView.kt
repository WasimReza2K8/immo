@file:Suppress("MagicNumber")

package com.wasim.feature.realestate.presentation.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.R
import com.example.core.ui.theme.WasimTheme
import com.example.core.ui.views.ImageView
import com.example.core.ui.views.TextWithSuperScript

@Composable
internal fun PhotoWithInfoView(
    price: String,
    area: String,
    areaSuperScript: String,
    imageUrl: String,
    city: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(stringResource(id = R.string.photo_text_view))
    ) {
        ImageView(
            url = imageUrl,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(WasimTheme.spacing.spacing48)
                .align(Alignment.BottomStart)
                .background(color = WasimTheme.color.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = WasimTheme.spacing.spacing12),
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.70f),
                    text = price,
                    style = WasimTheme.typography.body1,
                    color = WasimTheme.color.grey200,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                TextWithSuperScript(
                    text = area,
                    superScript = areaSuperScript,
                    style = WasimTheme.typography.body1,
                    color = WasimTheme.color.grey200,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(start = WasimTheme.spacing.spacing12),
                text = city,
                style = WasimTheme.typography.caption,
                color = WasimTheme.color.grey200,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun ImageTextPreview() {
    WasimTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PhotoWithInfoView(
                price = "2000000",
                area = "600.0 m",
                modifier = Modifier,
                imageUrl = "imageUrl",
                city = "city",
                areaSuperScript = "2"
            )
        }
    }
}
