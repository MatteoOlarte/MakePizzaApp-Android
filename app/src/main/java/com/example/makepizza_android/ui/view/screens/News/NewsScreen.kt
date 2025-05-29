package com.example.makepizza_android.ui.view.screens.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.makepizza_android.domain.models.NewsArticle

@OptIn(ExperimentalMaterial3Api::class)
class NewsScreen : Screen {
    private val title = "Noticias"

    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val viewModel = viewModel<NewsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        // Fetch news on first load
        LaunchedEffect(Unit) {
            viewModel.fetchNews()
        }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { ScreenTopAppBar(scrollBehavior) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (uiState) {
                    is NewsUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is NewsUiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error al cargar noticias", color = MaterialTheme.colorScheme.error)
                        }
                    }

                    is NewsUiState.Success -> {
                        val articles = (uiState as NewsUiState.Success).news
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(articles) { article ->
                                NewsCard(article)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ScreenTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
        val navigator = LocalNavigator.current

        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = { navigator?.pop() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
            },
            scrollBehavior = scrollBehavior
        )
    }

    @Composable
    fun NewsCard(article: NewsArticle) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                // En el futuro podrías abrir el enlace en navegador
                // val context = LocalContext.current
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                // context.startActivity(intent)
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                article.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fuente: ${article.sourceName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
