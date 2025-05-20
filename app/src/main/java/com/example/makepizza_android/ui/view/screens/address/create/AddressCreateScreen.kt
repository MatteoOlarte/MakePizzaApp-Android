package com.example.makepizza_android.ui.view.screens.address.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator

class   AddressCreateScreen(val userID: String) : Screen {
    @Composable
    override fun Content() {
        val viewmodel = viewModel<AddressCreateViewModel>()
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle().value
        val navigator = LocalNavigator.current

        LaunchedEffect(uiState) {
            if (uiState == AddressCreateViewState.Success) navigator?.pop()
        }

        Scaffold(
            topBar = { ScreenTopAppBar(viewmodel, navigator) }
        ) {
            ScreenContent(modifier = Modifier.padding(it), viewModel = viewmodel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTopAppBar(
        viewModel: AddressCreateViewModel,
        navigator: Navigator?,
        modifier: Modifier = Modifier
    ) {
        val showName = viewModel.showCurrentTitle.observeAsState().value
        val appBarTitle = when (showName) {
            true -> viewModel.shippingAddressName.observeAsState(initial = "Nueva Dirección").value
            else -> "Nueva Dirección"
        }

        TopAppBar(
            modifier = modifier,
            title = { Text(text = appBarTitle) },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) { Icon(Icons.Filled.Close, "") }
            }
        )
    }

    @Composable
    private fun ScreenContent(viewModel: AddressCreateViewModel, modifier: Modifier = Modifier) {
        Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
            AddressValueTextField(viewModel)
            Spacer(modifier = Modifier.height(16.dp))

            AddressNameTextField(viewModel)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.handleCreateNewAddress(userID) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }
        }
    }

    @Composable
    private fun AddressValueTextField(viewModel: AddressCreateViewModel) {
        val addressValue = viewModel.shippingAddressValue.observeAsState(initial = "").value

        OutlinedTextField(
            value = addressValue,
            onValueChange = { viewModel.handleAddressValueOnChange(it) },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                autoCorrectEnabled = false
            ),
            placeholder = { Text("Cra 7 #32-45, Bogotá") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.50f)
            )
        )
    }

    @Composable
    private fun AddressNameTextField(viewModel: AddressCreateViewModel) {
        val addressName = viewModel.shippingAddressName.observeAsState(initial = "").value

        OutlinedTextField(
            value = addressName,
            onValueChange = { viewModel.handleAddressNameOnChange(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            placeholder = { Text("Casa, Trabajo, etc") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.50f)
            )
        )
    }
}