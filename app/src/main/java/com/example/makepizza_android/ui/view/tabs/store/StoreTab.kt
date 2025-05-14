package com.example.makepizza_android.ui.view.tabs.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.R
import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.ui.view.common.AddressItem
import com.example.makepizza_android.ui.view.common.BackGradient
import com.example.makepizza_android.ui.view.common.IngredientItem
import com.example.makepizza_android.ui.view.common.IngredientLoadingItem
import com.example.makepizza_android.ui.view.common.PizzaListItem
import com.example.makepizza_android.ui.view.common.PizzaListItemLoading
import com.example.makepizza_android.ui.view.common.TitleBox
import com.example.makepizza_android.ui.view.screens.pizza.PizzaDetailScreen
import com.example.makepizza_android.ui.view.tabs.customize.CustomizeTab

object StoreTab : Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val viewModel = viewModel<StoreTabViewmodel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val showModal by viewModel.showAddressesModal.observeAsState(initial = false)
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        val contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(
            NavigationBarDefaults.windowInsets
        )

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) viewModel.fetchData()
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            contentWindowInsets = contentWindowInsets
        ) {
            TabContent(
                modifier = Modifier.Companion.padding(it),
                viewModel = viewModel,
                uiState = uiState
            )

            if (showModal) {
                BottomSheetList(viewModel = viewModel, sheetState = sheetState)
            }
        }
    }

    @Composable
    private fun TabContent(
        viewModel: StoreTabViewmodel,
        uiState: StoreTabState,
        modifier: Modifier = Modifier
    ) {
        val navigator = LocalNavigator.currentOrThrow.parent
        val ingredients by viewModel.ingredients.observeAsState(initial = emptyList())
        val pizzas by viewModel.pizzas.observeAsState(initial = emptyList())

        val isLoading = when (uiState) {
            StoreTabState.Loading -> true
            is StoreTabState.Error -> true
            else -> false
        }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TabToolbar(viewModel = viewModel) }
            item { Banner() }

            this.showIngredientsRow(ingredients, isLoading)
            this.showPizzasList(pizzas, isLoading) { navigator?.push(PizzaDetailScreen(it)) }
        }
    }

    @Composable
    private fun TabToolbar(
        viewModel: StoreTabViewmodel,
        modifier: Modifier = Modifier
    ) {
        val address by viewModel.currentAddress.observeAsState(initial = null)

        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = address?.addressValue ?: "No hay Dirección Selecionada",
                    style = MaterialTheme.typography.bodyMedium.copy()
                )
                Text(
                    text = address?.addressName ?: "Agrega una Dirección",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Companion.SemiBold
                    )
                )
            }

            FilledTonalIconButton(onClick = { viewModel.handleAddressClick() }) {
                Icon(Icons.Filled.EditLocationAlt, "")
            }
        }
    }

    @Composable
    private fun Banner(modifier: Modifier = Modifier) {
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
    private fun ImageBox() {
        val navigator = LocalTabNavigator.current

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "contentDescription",
                contentScale = ContentScale.Companion.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BackGradient(
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Crear tus Propias Pizzas",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "No se que poner aqui",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White
                        )
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                ElevatedButton(onClick = { navigator.current = CustomizeTab }) {
                    Text(text = "Ver mas")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun BottomSheetList(
        viewModel: StoreTabViewmodel,
        sheetState: SheetState,
        modifier: Modifier = Modifier
    ) {
        val addresses by viewModel.savedAddresses.observeAsState(initial = emptyList())

        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = { viewModel.handleOnDismissRequest() }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(addresses) {
                    AddressItem(it, onClick = { viewModel.handleAddressSelected(it.id) })
                }
            }
        }
    }

    private fun LazyListScope.showIngredientsRow(
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

    private fun LazyListScope.showPizzasList(
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