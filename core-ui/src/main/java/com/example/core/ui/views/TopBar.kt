package com.example.core.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.WasimTheme

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        contentColor = Color.White,
        title = {
            Column {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        },
        modifier = modifier,
        navigationIcon = onNavigateUp?.let {
            {
                IconButton(
                    onClick = it,
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = actions,
    )
}

@Preview
@Composable
fun TopBarDemo() {
    val title = "Property"
    WasimTheme {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(16.dp),
            modifier = Modifier.wrapContentSize()
        ) {
            TopBar(
                title,
            )
            TopBar(
                title,
                onNavigateUp = {}
            )
            TopBar(
                title,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    }
                },
            )
        }
    }
}
