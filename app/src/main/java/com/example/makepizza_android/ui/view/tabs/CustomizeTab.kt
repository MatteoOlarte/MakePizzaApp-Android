package com.example.makepizza_android.ui.view.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.ui.theme.ApplicationTheme

object CustomizeTab: Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold (
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {TabToolbar(scrollBehavior = scrollBehavior)},
        ) {
            TabContent(modifier = Modifier.padding(top = it.calculateTopPadding()))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabToolbar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            title = { Text(text = "My Pizzas") },
            scrollBehavior = scrollBehavior,
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Add, "Add New Custom Pizza")
                }
            }
        )
    }

    @Composable
    fun TabContent(modifier: Modifier) {
        val pizzas = (1..100).toList()

        LazyColumn (
            modifier = modifier.fillMaxSize()
        ) {
            items (pizzas) {
                Text(text = "Pizza $it")
            }
        }
    }

    @Composable
    fun PizzaItem() {

    }


    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = "Customize"
        val icon = rememberVectorPainter(Icons.Filled.DashboardCustomize)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    private fun readResolve(): Any = CustomizeTab
}

@Preview(device = Devices.PIXEL_6, showSystemUi = true)
@Composable
private fun CustomizeTabPreview() {
    ApplicationTheme { CustomizeTab.Content() }
}
