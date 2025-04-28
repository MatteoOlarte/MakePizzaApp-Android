package com.example.makepizza_android.ui.view.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BackGradient(modifier: Modifier = Modifier) {
    val colors = listOf<Color>(Color.Companion.Transparent, Color.Companion.Black)
    val brush = Brush.Companion.verticalGradient(colors, startY = 100f)

    Box(
        modifier = modifier.background(brush)
    )
}