package com.example.makepizza_android.ui.view.screens.register

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.theme.ApplicationTheme

class RegisterScreen: Screen {
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
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Regitro") },
            navigationIcon = {
                IconButton(onClick = {navigator?.pop()}) {
                    Icon(Icons.Filled.Close, "")
                }
            },
            actions = {

            }
        )
    }

    @Composable
    fun ScreenContent(modifier: Modifier) {

    }
}

@Preview(device = Devices.PIXEL_6A, showSystemUi = true, uiMode = 0x20)
@Composable
fun PreviewRegisterScreen() {
    ApplicationTheme { RegisterScreen().Content() }
}