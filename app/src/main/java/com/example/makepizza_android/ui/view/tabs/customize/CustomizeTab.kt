package com.example.makepizza_android.ui.view.tabs.customize

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.example.makepizza_android.ui.view.common.LoginRequired
import com.example.makepizza_android.ui.view.common.PizzaListItem
import com.example.makepizza_android.ui.view.common.PizzaListItemLoading
import com.example.makepizza_android.ui.view.screens.login.LoginScreen

object CustomizeTab: Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(
            NavigationBarDefaults.windowInsets
        )
        val viewmodel = viewModel<CustomizeTabViewModel>()

        Scaffold(
            modifier = Modifier.Companion.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TabToolbar(scrollBehavior = scrollBehavior) },
            floatingActionButton = { TabFAB() },
            contentWindowInsets = contentWindowInsets
        ) {
            TabContent(
                modifier = Modifier.Companion.padding(it),
                viewmodel = viewmodel
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabToolbar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            title = { Text(text = "Personalizadas") },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    fun TabFAB(modifier: Modifier = Modifier) {
        FloatingActionButton(
            modifier = modifier,
            onClick = {},
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(Icons.Filled.Add, "ADD")
        }
    }

    @Composable
    fun TabContent(viewmodel: CustomizeTabViewModel, modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current?.parent
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
        val userLogged = when (uiState.value) {
            CustomizeTabState.Success(hasCurrentUser = true) -> true
            else -> false
        }
        val isLoading = when (uiState.value) {
            CustomizeTabState.Loading -> true
            else -> false
        }

        if  (isLoading) {
            ShowLoading(modifier = modifier)
        } else {
            if (userLogged) {
                ShowContent(modifier = modifier, viewmodel = viewmodel)
            } else {
                LoginRequired(toLogin = { navigator?.push(LoginScreen()) }, modifier = modifier)
            }
        }
    }

    @Composable
    private fun ShowLoading(modifier: Modifier = Modifier) {
        val pizzas = (1..5).toList()

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(pizzas) { PizzaListItemLoading() }
        }
    }

    @Composable
    private fun ShowContent(viewmodel: CustomizeTabViewModel, modifier: Modifier = Modifier) {
        val pizzas = viewmodel.pizzas.observeAsState(initial = emptyList()).value

        LazyColumn (
            modifier = modifier
        ) {
            items (pizzas) { PizzaListItem(it, {}) }
        }
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