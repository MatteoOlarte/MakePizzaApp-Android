package com.example.makepizza_android.ui.view.screens.address.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.ui.view.screens.address.create.AddressCreateScreen

class AddressScreen(val userID: String) : Screen {
    @Composable
    override fun Content() {
        val viewmodel = viewModel<AddressScreenViewModel>()
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewmodel.getAllUserAddresses(userID)
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            topBar = { ScreenTopAppBar() }
        ) {
            ScreenContent(
                modifier = Modifier.padding(it),
                viewModel = viewmodel
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTopAppBar() {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(text = "Dirección de envío") },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                }
            },
            actions = {
                IconButton(onClick = { navigator?.push(AddressCreateScreen(userID)) }) {
                    Icon(Icons.Filled.Add, "Nueva Dirección")
                }
            }
        )
    }

    @Composable
    private fun ScreenContent(viewModel: AddressScreenViewModel, modifier: Modifier = Modifier) {
        val addresses = viewModel.addressesList.observeAsState(initial = emptyList()).value

        if (addresses.isEmpty()) {
            EmptyListMessage(modifier = modifier)
            return
        }

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(addresses) { AddressItem(it) }
        }
    }

    @Composable
    private fun EmptyListMessage(modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No hay direcciónes guardadas")
                TextButton(onClick = { navigator?.push(AddressCreateScreen(userID)) }) {
                    Text(text = "Agregar Dirección")
                }
            }
        }
    }

    @Composable
    private fun AddressItem(address: Address) {
        Box(
            modifier = Modifier.fillMaxWidth().clickable(
                enabled = true,
                onClickLabel = "Opciones",
                onClick = {},
                role = Role.Button
            ).men
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = address.addressName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = address.addressValue,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}