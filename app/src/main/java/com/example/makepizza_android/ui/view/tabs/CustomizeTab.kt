package com.example.makepizza_android.ui.view.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object CustomizeTab: Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @Composable
    override fun Content() {

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