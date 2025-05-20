package com.example.makepizza_android.ui.view.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.ui.theme.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
class OrderScreen : Screen {
    private val title = "Pedidos"

    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { ScreenTopAppBar(scrollBehavior) }
        ) {
            ScreenContent(modifier = Modifier.padding(it))
        }
    }

    @Composable
    private fun ScreenTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
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
    private fun ScreenContent(modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf(1, 2, 3, 4)) { OrderListItem() }
        }
    }

    @Composable
    private fun OrderListItem(modifier: Modifier = Modifier) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "precio total",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "fecha",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "id orden",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                OrderStatusLabel()
            }
        }
    }

    @Composable
    private fun OrderStatusLabel() {
        Surface (
            shape = ShapeDefaults.Medium,
            color = Color(0xFF1ED70E)
        ) {
            Box(
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = "Entregado",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        background = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_7)
@Composable
private fun ScreenPreview() {
    ApplicationTheme { OrderScreen().Content() }
}