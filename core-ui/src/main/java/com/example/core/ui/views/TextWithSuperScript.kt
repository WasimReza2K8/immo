package com.example.core.ui.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.theme.WasimTheme

@Composable
fun TextWithSuperScript(
    text: String,
    superScript: String,
    modifier: Modifier = Modifier,
    style: TextStyle = WasimTheme.typography.body1,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(text)
            withStyle(WasimTheme.spanning.superscript) {
                append(superScript)
            }
        },
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview
@Composable
private fun TextWithSuperScriptPreview() {
    WasimTheme {
        TextWithSuperScript(text = "234 m", superScript = "2")
    }
}
