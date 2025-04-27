package com.example.makepizza_android.ui.view.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.R
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.common.TitleBox


object StoreTab : Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @Composable
    override fun Content() {
        Scaffold {
            TabContent(modifier = Modifier.padding(top = it.calculateTopPadding()))
        }
    }

    @Composable
    fun TabContent(modifier: Modifier) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                TabToolbar()
            }
            item {
                ImageBanner()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                TitleBox("Pizzas", modifier = Modifier.fillMaxWidth().padding(16.dp))
            }
            items((1..100).toList()) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    PizzaItem(text = "Item #$it")
                }
            }
        }
    }

    @Composable
    fun TabToolbar() {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodyMedium.copy()
                )
                Text(
                    text = "Agregar Direcion",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            FilledTonalIconButton (onClick = {}) {
                Icon(Icons.Filled.EditLocationAlt, "")
            }
        }
    }

    @Composable
    fun ImageBanner() {
        val colors = listOf<Color>(Color.Transparent, Color.Black)
        val brush = Brush.verticalGradient(colors, startY = 100f)

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().aspectRatio(1.3f),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "contentDescription",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier.fillMaxSize().background(brush)
                    )

                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        ElevatedButton(onClick = {}) {
                            Text(text = "Ver Mas")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PizzaItem(text: String, modifier: Modifier = Modifier) {
        val colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )

        Card(
            modifier = modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = colors,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Text(text = text)
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
private fun StoreTabPreview() {
    ApplicationTheme { StoreTab.Content() }
}

