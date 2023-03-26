package com.example.core.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter.State
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.core.ui.R
import com.example.core.ui.theme.WasimTheme

@Composable
fun ImageView(
    url: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .allowHardware(false)
                .build(),
            error = ColorPainter(Color.LightGray)
        )
        Image(
            painter = painter,
            contentScale = contentScale,
            contentDescription = stringResource(id = R.string.big_photo_content),
            modifier = modifier.fillMaxSize()
        )
        if (painter.state is State.Error) {
            Text(text = stringResource(id = R.string.no_image_found))
        }
    }
}

@Preview
@Composable
private fun ImageViewPreview() {
    WasimTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ImageView(
                url = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier,
            )
        }
    }
}
