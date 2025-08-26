package com.example.makepizza_android.ui.view.tabs.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.domain.models.Cart
import com.example.makepizza_android.ui.view.common.ShoppingCartItemView
import com.example.makepizza_android.ui.view.screens.checkout.CheckOutScreen
import java.text.NumberFormat
import java.util.Locale

object










































CartTab : Tab {
    private const val title = "Carrito"

    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = title
        val icon = rememberVectorPainter(Icons.Filled.ShoppingCart)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    @Composable
    override fun Content() {
        val contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(
            NavigationBarDefaults.windowInsets
        )
        val lifecycleOwner = LocalLifecycleOwner.current
        val viewModel = viewModel<CartTabViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) viewModel.fetchData()
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            topBar = { TabTopAppBar(viewModel) },
            contentWindowInsets = contentWindowInsets
        ) {
            TabContent(
                modifier = Modifier.padding(it),
                viewModel = viewModel,
                uiState = uiState
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TabTopAppBar(
        viewModel: CartTabViewModel,
        modifier: Modifier = Modifier
    ) {
        val itemsSize = viewModel.cartLength.observeAsState(initial = 0).value

        TopAppBar(
            modifier = modifier,
            title = { Text(text = "Carrito ($itemsSize)") }
        )
    }

    @Composable
    private fun TabContent(
        viewModel: CartTabViewModel,
        uiState: CartTabViewState,
        modifier: Modifier = Modifier
    ) {
        val cartItems = viewModel.cart.observeAsState(initial = emptyMap()).value

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartMessage(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    this.addCartItems(viewModel, cartItems)
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )

            CartCheckout(
                viewModel = viewModel,
                uiState = uiState
            )
        }
    }

    @Composable
    private fun EmptyCartMessage(
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito vacío",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tu carrito está vacío",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    @Composable
    private fun CartCheckout(
        viewModel: CartTabViewModel,
        uiState: CartTabViewState
    ) {
        val navigator = LocalNavigator.currentOrThrow.parent
        val total = viewModel.total.observeAsState().value
        val loading = when (uiState) {
            CartTabViewState.Loading -> true
            CartTabViewState.Error -> true
            else -> false
        }
        val format = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }.format(total)

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "COP $format",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = { navigator?.push(CheckOutScreen()) },
                enabled = !(loading || total == 0.0)
            ) {
                Text(text = "Proceder al pago")
            }
        }
    }

    private fun LazyListScope.addCartItems(
        viewModel: CartTabViewModel,
        cartItems: Map<Cart, Boolean>
    ) {
        items(cartItems.toList()) { (item, isChecked) ->
            ShoppingCartItemView(
                data = item,
                checked = isChecked,
                onCheckedChange = { viewModel.handleOnItemChecked(item, it) },
                onDeleteClick = { viewModel.handleDeleteItemClick(it) }
            )
        }
    }

    private fun readResolve(): Any = CartTab
}