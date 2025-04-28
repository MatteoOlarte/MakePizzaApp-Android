package com.example.makepizza_android.ui.view.tabs.cart

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object CartTab: Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @Composable
    override fun Content() {

    }

    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = "Cart"
        val icon = rememberVectorPainter(Icons.Filled.ShoppingCart)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    private fun readResolve(): Any = CartTab
}