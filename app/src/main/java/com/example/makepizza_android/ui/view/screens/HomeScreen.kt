package com.example.makepizza_android.ui.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.tabs.AccountTab
import com.example.makepizza_android.ui.view.tabs.CouponsTab
import com.example.makepizza_android.ui.view.tabs.CustomizeTab
import com.example.makepizza_android.ui.view.tabs.StoreTab

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(StoreTab) {
            Scaffold(
                bottomBar = { ScreenNavBar() }
            ) {
                ScreenContent(modifier = Modifier.padding(bottom = it.calculateBottomPadding()))
            }
        }
    }

    @Composable
    fun ScreenNavBar() {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 0.dp
            ) {
                TabNavigationItem(StoreTab)
                TabNavigationItem(CustomizeTab)
                TabNavigationItem(CouponsTab)
                TabNavigationItem(AccountTab)
            }
        }
    }

    @Composable
    fun ScreenContent(modifier: Modifier) {
        Box (modifier = modifier.fillMaxSize()) {
            CurrentTab()
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = "") },
            label = { Text(text = tab.options.title) },
        )
    }
}

@Preview(device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    ApplicationTheme { HomeScreen().Content() }
}