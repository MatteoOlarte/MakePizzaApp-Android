package com.example.makepizza_android.ui.view.screens.pizzapersonalizada

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class PizzaBuilderScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = viewModel<PizzaBuilderScreenViewModel>()

        Scaffold(
            topBar = { ScreenTopAppBar() }
        ) {
            ScreenContent(
                modifier = Modifier.padding(it),
                viewModel=viewModel
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTopAppBar() {
        TopAppBar(
            title = { Text(text = "Perzonaliza tu pizza") },
            navigationIcon = { ScreenTopAppBarNavIcon() }
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
    private fun ScreenContent(
        viewModel: PizzaBuilderScreenViewModel,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { PizzaNameField()  }
            item { SizesSelector(viewModel) }
            item { DougSelector(viewModel) }
            item { SauceSelector(viewModel) }
            item { IngredientSelector(viewModel) }
            item { IsPredefinedCheckBox() }
        }
    }

    @Composable
    private fun PizzaNameField() {
        Column (
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ){
            Text("Nombre de la pizza", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = "value",
                onValueChange = {  },
                label = { Text("Nombre Completo*") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun SizesSelector(viewModel: PizzaBuilderScreenViewModel) {
        val sizes = listOf("xl", "l", "m", "s")

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Tamaño", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sizes.forEach { size ->
                    FilterChip(
                        selected = false,
                        onClick = {  },
                        label = { Text(size) }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun DougSelector(viewModel: PizzaBuilderScreenViewModel) {
        val drugs by viewModel.drugs.observeAsState(initial = emptyList())

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Masa", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                drugs.forEach { size ->
                    FilterChip(
                        selected = false,
                        onClick = {  },
                        label = { Text(text = size.name) }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun SauceSelector(viewModel: PizzaBuilderScreenViewModel) {
        val drugs = listOf("Salsa de Tomate")

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Salsa", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                drugs.forEach { size ->
                    FilterChip(
                        selected = false,
                        onClick = {  },
                        label = { Text(size) }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun IngredientSelector(viewModel: PizzaBuilderScreenViewModel) {
        val drugs = listOf("Agrega Ingredientes")

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Agrega Ingredientes", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                drugs.forEach { size ->
                    FilterChip(
                        selected = false,
                        onClick = {  },
                        label = { Text(size) }
                    )
                }
            }
        }
    }

    @Composable
    private fun IsPredefinedCheckBox() {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = true,
                onCheckedChange = {}
            )
            Text(
                text = "Agregar al Catalogo (Admin Required)"
            )
        }
    }
}