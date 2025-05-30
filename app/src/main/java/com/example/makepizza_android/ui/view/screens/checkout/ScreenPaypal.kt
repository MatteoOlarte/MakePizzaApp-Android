package com.example.makepizza_android.ui.view.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay

class ScreenPaypal(
    private val onResult: (Boolean) -> Unit
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var isProcessing by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(2000) // Simula carga
            isProcessing = false
        }

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("PayPal") })
            }
        ) { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (isProcessing) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Procesando pago...")
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("¿Deseas confirmar el pago?", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Button(onClick = {
                                onResult(true)
                                navigator?.pop()
                            }) {
                                Text("Confirmar")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(onClick = {
                                onResult(false)
                                navigator?.pop()
                            }) {
                                Text("Cancelar")
                            }
                        }
                    }
                }
            }
        }
    }
}