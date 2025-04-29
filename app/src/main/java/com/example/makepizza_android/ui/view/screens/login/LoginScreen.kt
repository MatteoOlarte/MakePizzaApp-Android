package com.example.makepizza_android.ui.view.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.screens.home.HomeScreen

class LoginScreen: Screen {
    @Composable
    override fun Content() {
        Scaffold (
            topBar = {ScreenToolbar()}
        ) {
            ScreenContent(modifier = Modifier.padding(it))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScreenToolbar() {
        TopAppBar(
            title = { Text(text = "Login") }
        )
    }

    @Composable
    fun  ScreenContent(modifier: Modifier = Modifier) {

    }
}

@Preview(device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ApplicationTheme { HomeScreen().Content() }
}