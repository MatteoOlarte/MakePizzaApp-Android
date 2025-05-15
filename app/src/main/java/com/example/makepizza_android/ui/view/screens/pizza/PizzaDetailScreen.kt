package com.example.makepizza_android.ui.view.screens.pizza

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.R
import com.example.makepizza_android.data.remote.models.IngredientModel
import com.example.makepizza_android.data.remote.models.PizzaModel
import com.example.makepizza_android.ui.view.common.BackGradient


class PizzaDetailScreen(val uid: String, val isCustom: Boolean = false) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewmodel = viewModel<PizzaDetailViewModel>()
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle().value
        val navigator = LocalNavigator.current

        LifecycleEffectOnce { viewmodel.fetchData(uid, isCustom) }

        LaunchedEffect(uiState) { if (uiState == PizzaDetailViewState.CartClicked) navigator?.pop() }

        Scaffold(
            topBar = { ScreenTopBar() },
            bottomBar = { ScreenBottomBar(viewmodel) }
        ) {
            ScreenContent(
                modifier = Modifier.padding(it),
                viewmodel = viewmodel,
                uiState = uiState
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenTopBar() {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text("Información Producto") },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                }
            }
        )
    }

    @Composable
    private fun ScreenBottomBar(
        viewModel: PizzaDetailViewModel,
        modifier: Modifier = Modifier
    ) {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )

            BottomAppBar(
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.background,
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalIconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Share,
                            "",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Button(onClick = { viewModel.handleAddToCartClick() }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            "",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Agregar")
                    }
                }
            }
        }
    }

    @Composable
    private fun ScreenContent(
        viewmodel: PizzaDetailViewModel,
        uiState: PizzaDetailViewState,
        modifier: Modifier = Modifier
    ) {
        val data = viewmodel.currentPizzaModel.observeAsState().value

        val isLoading = when (uiState) {
            PizzaDetailViewState.Loading -> true
            PizzaDetailViewState.Error -> true
            else -> false
        }

        if (isLoading || data == null) {
            ShowLoading(modifier = modifier)
        } else {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Image(data) }
                item { Info(data) }
                this.showPizzaIngredients(data)
            }
        }
    }

    @Composable
    private fun ShowLoading(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Cargando Datos...")
        }
    }

    @Composable
    private fun Image(data: PizzaModel, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
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
                        contentScale = ContentScale.Companion.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    BackGradient(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    @Composable
    private fun Info(data: PizzaModel) {
        Column {
            Text(
                text = data.name,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineLarge.copy()
            )
            Text(
                text = data.desc ?: "...",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }

    @Composable
    private fun IngredientListItem(data: IngredientModel, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier.height(45.dp).width(45.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "contentDescription",
                        contentScale = ContentScale.Companion.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = data.name,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Visible
                        )
                        Text(
                            text = "$${data.price}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }

    private fun LazyListScope.showPizzaIngredients(data: PizzaModel) {
        this.item {
            Text(
                text = "Precio",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy()
            )
            Text(
                text = "$${data.price}",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
            )
        }

        this.item {
            Text(
                text = "Tamaño",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy()
            )
            Text(
                text = data.size.uppercase(),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
            )
        }

        this.item {
            Text(
                text = "Masa",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy()
            )
            Text(
                text = data.dough.name,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
            )
        }

        this.item {
            Text(
                text = "Salsa",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy()
            )
            Text(
                text = data.sauce.name,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
            )
        }

        this.item {
            Text(
                text = "Ingredientes",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy()
            )
        }

        this.items(data.ingredients) {
            IngredientListItem(it, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
