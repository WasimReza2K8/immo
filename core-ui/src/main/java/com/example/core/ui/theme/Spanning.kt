package com.example.core.ui.theme

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp

data class Spanning(
    val superscript: SpanStyle = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 12.sp
    ),
)
