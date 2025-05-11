package com.example.makepizza_android.ui.view.screens.address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class AddressScreen : Screen {
    @Composable
    override fun Content() {
        val viewmodel = viewModel<AddressScreenViewModel>()
        val showBottomSheet = viewmodel.showBottomSheet.observeAsState(initial = false)

        Scaffold(
            topBar = { ScreenTopAppBar(viewmodel = viewmodel) }
        ) {
            if (showBottomSheet.value) {
                ScreenBottomSheet(viewmodel = viewmodel)
            }

            ScreenContent(
                modifier = Modifier.padding(it)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTopAppBar(
        viewmodel: AddressScreenViewModel
    ) {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Dirección de envío") },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                }
            },
            actions = {
                IconButton(onClick = { viewmodel.setBottomSheetVisibility(true) }) {
                    Icon(Icons.Filled.Add, "Nueva Dirección")
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenBottomSheet(
        viewmodel: AddressScreenViewModel,
        modifier: Modifier = Modifier
    ) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { viewmodel.handleOnDismissRequest() },
            sheetState = sheetState
        ) {
            Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
                Text(text = "Nueva Dirección", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = "name",
                    onValueChange = { },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "address",
                    onValueChange = {},
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewmodel.setBottomSheetVisibility(false) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar")
                }
            }
        }
    }

    @Composable
    private fun ScreenContent(modifier: Modifier = Modifier) {

    }
}