package com.example.makepizza_android.ui.view.screens.checkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen

class CheckOutScreen: Screen {
    private val title = "Checkout"

    @Composable
    override fun Content() {

    }
}


@Preview
@Composable
fun CheckOutScreenPreview() {
    CheckOutScreen().Content()
}