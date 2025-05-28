package com.example.makepizza_android.ui.view.tabs.customize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.ui.view.common.LoginRequired
import com.example.makepizza_android.ui.view.common.PizzaListItem
import com.example.makepizza_android.ui.view.common.PizzaListItemLoading
import com.example.makepizza_android.ui.view.screens.login.LoginScreen
import com.example.makepizza_android.ui.view.screens.pizza.PizzaDetailScreen
import com.example.makepizza_android.ui.view.screens.pizzapersonalizada.PizzaBuilderScreen

object CustomizeTab : Tab {
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
        val viewModel = viewModel<CustomizeTabViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.fetchData()
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TabToolbar(scrollBehavior = scrollBehavior) },
            floatingActionButton = { TabFAB() },
            contentWindowInsets = contentWindowInsets
        ) {
            TabContent(
                modifier = Modifier.padding(it),
                viewModel = viewModel,
                uiState = uiState.value
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabToolbar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            title = { Text(text = "Pizzas Personalizadas") },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    fun TabFAB(modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.current?.parent

        FloatingActionButton(
            modifier = modifier,
            onClick = { navigator?.push(PizzaBuilderScreen()) },
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Crear nueva pizza")
        }
    }

    @Composable
    fun TabContent(
        viewModel: CustomizeTabViewModel,
        uiState: CustomizeTabState,
        modifier: Modifier = Modifier
    ) {
        val navigator = LocalNavigator.current?.parent
        val isLoading = when (uiState) {
            CustomizeTabState.Success(hasCurrentUser = true) -> false
            CustomizeTabState.Success(hasCurrentUser = false) -> false
            else -> true
        }
        val userLogged = when (uiState) {
            CustomizeTabState.Success(hasCurrentUser = true) -> true
            else -> false
        }
        val pizzas = viewModel.pizzas.observeAsState(initial = emptyList()).value

        if (isLoading) {
            ShowLoading(modifier = modifier)
        } else {
            if (userLogged) {
                ShowContent(
                    modifier = modifier,
                    data = pizzas,
                    navigateTo = { navigator?.push(PizzaDetailScreen(it, true)) }
                )
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
    private fun ShowContent(
        data: List<PizzaListModel>,
        navigateTo: (uid: String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val navigator = LocalNavigator.current?.parent

        if (data.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay pizzas personalizadas"
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = { navigator?.push(PizzaBuilderScreen()) })
                {
                    Text(text = "Crear nueva pizza")
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
            ) {
                items(data) { PizzaListItem(it, { navigateTo(it.uid) }) }
            }
        }
    }

    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = "Personalizar"
        val icon = rememberVectorPainter(Icons.Filled.DashboardCustomize)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    private fun readResolve(): Any = CustomizeTab
}
