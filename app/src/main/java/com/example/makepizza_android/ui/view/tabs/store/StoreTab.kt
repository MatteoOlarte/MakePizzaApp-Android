package com.example.makepizza_android.ui.view.tabs.store

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.R
import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.common.BackGradient
import com.example.makepizza_android.ui.view.common.IngredientItem
import com.example.makepizza_android.ui.view.common.IngredientLoadingItem
import com.example.makepizza_android.ui.view.common.PizzaListItem
import com.example.makepizza_android.ui.view.common.PizzaListItemLoading
import com.example.makepizza_android.ui.view.common.TitleBox
import com.example.makepizza_android.ui.view.screens.pizza.PizzaDetailScreen

object StoreTab : Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @Composable
    override fun Content() {
        val viewmodel = viewModel<StoreTabViewmodel>()

        Scaffold {
            TabContent(
                modifier = Modifier.Companion.padding(top = it.calculateTopPadding()),
                viewmodel = viewmodel
            )
        }
    }

    @Composable
    fun TabContent(modifier: Modifier, viewmodel: StoreTabViewmodel) {
        val navigator = LocalNavigator.currentOrThrow.parent
        val ingredients by viewmodel.ingredients.observeAsState(initial = emptyList())
        val pizzas by viewmodel.pizzas.observeAsState(initial = emptyList())
        val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
        val isLoading = when (uiState) {
            StoreTabState.Success -> false
            else -> true
        }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TabToolbar(viewmodel = viewmodel) }
            item { Banner() }

            this.showIngredientsRow(ingredients, isLoading)
            this.showPizzasList(pizzas, isLoading, { navigator?.push(PizzaDetailScreen(it)) })
        }
    }

    @Composable
    fun TabToolbar(viewmodel: StoreTabViewmodel, modifier: Modifier = Modifier) {
        val address by viewmodel.address.observeAsState(initial = "No hay ninguna dirección guardada")

        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = address!!,
                    style = MaterialTheme.typography.bodyMedium.copy()
                )
                Text(
                    text = "Agrega una dirección",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Companion.SemiBold
                    )
                )
            }
            FilledTonalIconButton(onClick = { viewmodel.handleAddressClick() }) {
                Icon(Icons.Filled.EditLocationAlt, "")
            }
        }
    }

    @Composable
    fun Banner(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().aspectRatio(1.3f),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            ) {
                ImageBox()
            }
        }
    }

    @Composable
    fun ImageBox() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "contentDescription",
                contentScale = ContentScale.Companion.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BackGradient(
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                ElevatedButton(onClick = {}) { Text(text = "Ver mas") }
            }
        }
    }

    fun LazyListScope.showIngredientsRow(
        ingredients: List<IngredientListModel>,
        isLoading: Boolean = true
    ) {
        item {
            TitleBox(
                "Ingredientes Especiales",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (!isLoading) {
                    items(ingredients) { IngredientItem(it) }
                } else {
                    items((1..7).toList()) { IngredientLoadingItem() }
                }
            }
        }
    }

    fun LazyListScope.showPizzasList(
        pizzas: List<PizzaListModel>,
        isLoading: Boolean = true,
        navigateTo: (uid: String) -> Unit = {}
    ) {
        item { TitleBox("Pizzas Populares", modifier = Modifier.padding(horizontal = 16.dp)) }

        if (!isLoading) {
            items(pizzas) { PizzaListItem(pizzaModel = it, onClick = { navigateTo(it.uid) }) }
        } else {
            items((1..7).toList()) { PizzaListItemLoading() }
        }
    }

    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = "Store"
        val icon = rememberVectorPainter(Icons.Filled.Store)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    private fun readResolve(): Any = StoreTab
}

@Preview(device = Devices.PIXEL_6, showSystemUi = true)
@Composable
private fun StoreTabPreviewLightMode() {
    ApplicationTheme { StoreTab.Content() }
}

@Preview(device = Devices.PIXEL_6, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoreTabPreviewDarkMode() {
    ApplicationTheme { StoreTab.Content() }
}

