package com.example.makepizza_android.ui.view.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.screens.register.RegisterScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewmodel = viewModel<LoginScreenViewModel>()

        Scaffold(
            topBar = { ScreenToolbar() },
            bottomBar = { ScreenBottomBar(viewmodel) }
        ) {
            ScreenContent(
                modifier = Modifier.padding(it),
                viewmodel = viewmodel
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScreenToolbar() {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Ingresar") },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(Icons.Filled.Close, "")
                }
            },
            actions = {

            }
        )
    }

    @Composable
    fun ScreenContent(viewmodel: LoginScreenViewModel, modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.value) {
            if (uiState.value == LoginScreenState.Success) { navigator?.pop() }
        }

        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
        ) {
            UserNameTextFiled(viewmodel = viewmodel)

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextFiled(viewmodel = viewmodel)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { }) {
                    Text(text = "Olvide Contrasena")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { navigator?.replace(RegisterScreen()) }) {
                    Text(text = "Crear Cuenta")
                }
            }
        }
    }

    @Composable
    fun ScreenBottomBar(viewmodel: LoginScreenViewModel) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { viewmodel.handleOnDone() }) {
                    Text(text = "Continuar")
                }
            }
        }
    }

    @Composable
    private fun UserNameTextFiled(viewmodel: LoginScreenViewModel) {
        val username = viewmodel.username.observeAsState(initial = "").value
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
        val isError = when (uiState.value) {
            LoginScreenState.InvalidCredentials -> true
            LoginScreenState.MissingEmail -> true
            else -> false
        }
        val supportText = when (uiState.value) {
            LoginScreenState.MissingEmail -> "Correo Invalido"
            LoginScreenState.InvalidCredentials -> "Correo o Costrasena invalidos"
            else -> null
        }

        OutlinedTextField(
            value = username,
            onValueChange = { viewmodel.handleUserNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            isError = isError,
            supportingText = { if (isError) Text(supportText!!) else null }
        )
    }

    @Composable
    private fun PasswordTextFiled(viewmodel: LoginScreenViewModel) {
        val password = viewmodel.password.observeAsState(initial = "").value
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
        var show by remember { mutableStateOf(false) }
        var visualTransformation = if (show) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
        val isError = when (uiState.value) {
            LoginScreenState.InvalidCredentials -> true
            LoginScreenState.MissingPassword -> true
            else -> false
        }

        OutlinedTextField(
            value = password,
            onValueChange = { viewmodel.handlePasswordChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewmodel.handleOnDone() }
            ),
            visualTransformation = visualTransformation,
            singleLine = true,
            trailingIcon = {
                PasswordTextFiledTrailingIcon(show, onClick = { show = !show })
            },
            isError = isError,
            supportingText = { Text(text = "Minimo de 8 caracteres etc") }
        )
    }

    @Composable
    private fun PasswordTextFiledTrailingIcon(show: Boolean, onClick: () -> Unit) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (show) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = if (show) "Hide password" else "Show password"
            )
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