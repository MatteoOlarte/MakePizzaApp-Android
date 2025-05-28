package com.example.makepizza_android.ui.view.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.view.common.ContentLoading

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = viewModel<RegisterScreenViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val loading = when (uiState) {
            RegisterScreenViewState.Loading -> true
            RegisterScreenViewState.OnError("NetworkError") -> true
            else -> false
        }

        LaunchedEffect(uiState) {
            if (uiState == RegisterScreenViewState.Success) {
                navigator?.pop()
            }
        }

        Scaffold(
            topBar = { ScreenToolbar() },
            bottomBar = { ScreenBottomBar(viewModel, uiState) }
        ) {
            if (loading) {
                ContentLoading(
                    modifier = Modifier.padding(it)
                )
            } else {
                ScreenContent(
                    modifier = Modifier.padding(it),
                    viewModel = viewModel,
                    uiSate = uiState
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenToolbar() {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Crear Cuenta") },
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
    fun ScreenBottomBar(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState
    ) {
        val enabled = when (uiSate) {
            RegisterScreenViewState.Loading -> false
            RegisterScreenViewState.OnError("NetworkError") -> false
            else -> true
        }
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { viewModel.handleOnDone() },
                    enabled = enabled
                ) {
                    Text(text = "Crear Cuenta")
                }
            }
        }
    }

    @Composable
    private fun ScreenContent(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState,
        modifier: Modifier
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
        ) {
            NameField(viewModel, uiSate)
            EmailField(viewModel, uiSate)
            Password1Field(viewModel, uiSate)
            Password2Field(viewModel, uiSate)
        }
    }

    @Composable
    private fun NameField(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState
    ) {
        val value by viewModel.name.observeAsState(initial = "")
        val error = when (uiSate) {
            RegisterScreenViewState.OnError("UserNameIsEmpty") -> true
            else -> false
        }

        OutlinedTextField(
            value = value,
            onValueChange = { viewModel.handleNameChange(it) },
            label = { Text("Nombre Completo*") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = error
        )
    }

    @Composable
    private fun EmailField(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState
    ) {
        val value by viewModel.email.observeAsState(initial = "")
        val error = when (uiSate) {
            RegisterScreenViewState.OnError("EmailIsEmpty") -> true
            else -> false
        }

        OutlinedTextField(
            value = value,
            onValueChange = { viewModel.handleEmailChange(it) },
            label = { Text("Correo Electrónico*") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = error
        )
    }

    @Composable
    private fun Password1Field(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState
    ) {
        val value by viewModel.password1.observeAsState(initial = "")
        val error = when (uiSate) {
            RegisterScreenViewState.OnError("Password1IsEmpty") -> true
            RegisterScreenViewState.OnError("PasswordsMismatch") -> true
            else -> false
        }

        OutlinedTextField(
            value = value,
            onValueChange = { viewModel.handlePassword1Change(it) },
            label = { Text("Contraseña*") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            isError = error
        )
    }

    @Composable
    private fun Password2Field(
        viewModel: RegisterScreenViewModel,
        uiSate: RegisterScreenViewState
    ) {
        val value by viewModel.password2.observeAsState(initial = "")
        val error = when (uiSate) {
            RegisterScreenViewState.OnError("Password2IsEmpty") -> true
            RegisterScreenViewState.OnError("PasswordsMismatch") -> true
            else -> false
        }

        OutlinedTextField(
            value = value,
            onValueChange = { viewModel.handlePassword2Change(it) },
            label = { Text("Contraseña*") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.handleOnDone() }
            ),
            isError = error
        )
    }
}