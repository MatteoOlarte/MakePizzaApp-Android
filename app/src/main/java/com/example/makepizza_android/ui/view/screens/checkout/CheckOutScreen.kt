package com.example.makepizza_android.ui.view.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.view.common.ContentLoading
import com.example.makepizza_android.ui.view.screens.address.list.AddressScreen
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class CheckOutScreen : Screen {
    private val title = "Checkout"

    @Composable
    override fun Content() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val viewModel = viewModel<CheckOutViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
        val loading = when (uiState) {
            CheckOutViewState.Loading -> true
            is CheckOutViewState.OnError -> true
            else -> false
        }
        val navigator = LocalNavigator.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_CREATE) viewModel.createNewOrder()
                if (event == Lifecycle.Event.ON_RESUME) viewModel.fetchData()
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        LaunchedEffect(uiState) {
            if (uiState == CheckOutViewState.Updated) navigator?.pop()
        }

        Scaffold(
            topBar = { ScreenTopAppBar() },
            bottomBar = { ScreenBottomAppBar(viewModel, loading) }
        ) {
            if (loading) {
                ContentLoading(modifier = Modifier.padding(it))
            } else {
                ScreenContent(viewModel, uiState, modifier = Modifier.padding(it))
            }
        }
    }

    @Composable
    private fun ScreenTopAppBar(
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = { ScreenTopAppBarNavIcon() },
            modifier = modifier
        )
    }

    @Composable
    private fun ScreenTopAppBarNavIcon() {
        val navigator = LocalNavigator.current

        IconButton(onClick = { navigator?.pop() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
        }
    }

    @Composable
    private fun ScreenBottomAppBar(
        viewModel: CheckOutViewModel,
        loading: Boolean,
        modifier: Modifier = Modifier
    ) {
        val totalVale by viewModel.total.observeAsState(initial = 0.0)
        val format = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }.format(totalVale.times(4200))

        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Precio Total",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$$format",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = modifier.width(8.dp))
                        Text(
                            text = "IVA incluido (19%)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                    }
                }

                Button(
                    onClick = { viewModel.handleDoneClick() },
                    enabled = !loading
                ) {
                    Text(text = "Comprar")
                }
            }
        }
    }

    @Composable
    private fun ScreenContent(
        viewModel: CheckOutViewModel,
        uiState: CheckOutViewState,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            this.item { NameFields(viewModel) }
            this.item { AddressField(viewModel) }
            this.item { PhoneNumberField(viewModel) }
            this.item { TipAmountField(viewModel) }
        }
    }

    @Composable
    private fun NameFields(viewModel: CheckOutViewModel) {
        val fNameValue by viewModel.fName.observeAsState(initial = "")
        val lNameValue by viewModel.lName.observeAsState(initial = "")

        Row {
            OutlinedTextField(
                value = fNameValue,
                onValueChange = { viewModel.handleFNameFieldChanged(it) },
                label = { Text("Nombre *") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = lNameValue,
                onValueChange = { viewModel.handleLNameFieldChanged(it) },
                label = { Text("Apellido *") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
        }
    }

    @Composable
    private fun AddressField(viewModel: CheckOutViewModel) {
        val navigator = LocalNavigator.current
        val addressValue by viewModel.address.observeAsState(initial = "")
        val currentUser = viewModel.currentUser.value

        if (currentUser != null) {
            Column {
                OutlinedTextField(
                    value = addressValue,
                    onValueChange = { viewModel.handleAddressFieldChanged(it) },
                    label = { Text("Dirección de envío *") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                TextButton(onClick = { navigator?.push(AddressScreen(currentUser.uid)) }) {
                    Text(text = "Agregar apto., suite u otro")
                }
            }
        }
    }

    @Composable
    private fun PhoneNumberField(viewModel: CheckOutViewModel) {
        val phoneValue by viewModel.phone.observeAsState(initial = "")

        OutlinedTextField(
            value = phoneValue,
            onValueChange = { viewModel.handlePhoneFieldChanged(it) },
            label = { Text("Número de teléfono *") },
            modifier = Modifier.fillMaxWidth(),
            prefix = { Text(text = "+57") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
    }

    @Composable
    private fun TipAmountField(viewModel: CheckOutViewModel) {
        val tipValue by viewModel.tip.observeAsState(initial = 2000.0)

        OutlinedTextField(
            value = tipValue.toString(),
            onValueChange = { viewModel.handleTipFieldChanged(it.toDouble()) },
            label = { Text("Propina (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            prefix = { Text(text = "$") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { viewModel.handleDoneClick() }),
            singleLine = true
        )
    }
}
