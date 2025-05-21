package com.example.makepizza_android.ui.view.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.domain.models.OrderList
import com.example.makepizza_android.ui.view.common.ContentLoading
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class OrderScreen : Screen {
    private val title = "Pedidos"

    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val lifecycleOwner = LocalLifecycleOwner.current
        val viewModel = viewModel<OrderScreenViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
        val loading = when (uiState) {
            OrderScreenViewState.Loading -> true
            OrderScreenViewState.OnError("NetworkError") -> true
            else -> false
        }

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_CREATE) viewModel.fetchData()
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { ScreenTopAppBar(scrollBehavior) }
        ) {
            if (loading) {
                ContentLoading(modifier = Modifier.padding(it))
            } else {
                ScreenContent(viewModel = viewModel, modifier = Modifier.padding(it))
            }
        }
    }

    @Composable
    private fun ScreenTopAppBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = title) },
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
        viewModel: OrderScreenViewModel,
        modifier: Modifier = Modifier
    ) {
        val orders by viewModel.orders.observeAsState(initial = emptyList())

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(orders) { index, item ->
                if (index > 0) HorizontalDivider()
                OrderListItem(item)
            }
        }
    }

    @Composable
    private fun OrderListItem(
        data:  OrderList,
        modifier: Modifier = Modifier
    ) {
        val format = NumberFormat.getNumberInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }.format(data.totalPrice)

        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "COP $format",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = data.deliveryAddress,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = data.uid,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                OrderStatusLabel(data.status)
            }
        }
    }

    @Composable
    private fun OrderStatusLabel(status: String) {
        val color = when(status) {
            "created" -> Color.DarkGray
            "pending" -> Color.Yellow
            "preparing" -> Color.Blue
            "in_progress" -> Color.Cyan
            "delivered" -> Color(0xFF1ED70E)
            "cancelled" -> Color.Red
            else -> Color.Transparent
        }
        Surface(
            shape = ShapeDefaults.Medium,
            color = color
        ) {
            Box(
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        background = Color.Transparent
                    )
                )
            }
        }
    }
}
