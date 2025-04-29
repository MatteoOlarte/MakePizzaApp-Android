package com.example.makepizza_android.ui.view.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.screens.register.RegisterScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = { ScreenToolbar() },
            bottomBar = { ScreenBottomBar() }
        ) {
            ScreenContent(modifier = Modifier.padding(it))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScreenToolbar() {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Ingresar") },
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
    fun ScreenContent(modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current

        Column (
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {}) { Text(text = "Olvide Contrasena") }
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {navigator?.replace(RegisterScreen())}
                ) {
                    Text(text = "Crear Cuenta")
                }
            }
        }
    }

    @Composable
    fun ScreenBottomBar() {
        BottomAppBar (
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {}) { Text( text = "Continuar" ) }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
private fun LightModeLoginScreenPreview() {
    ApplicationTheme { LoginScreen().Content() }
}

@Preview(device = Devices.PIXEL_6A, showSystemUi = true, uiMode = 0x20)
@Composable
private fun DarkModeLoginScreenPreview() {
    ApplicationTheme { LoginScreen().Content() }
}