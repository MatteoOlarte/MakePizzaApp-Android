package com.example.makepizza_android.ui.view.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.NewsArticle
import com.example.makepizza_android.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val news: List<NewsArticle>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                val news = repository.fetchGastronomyNews()
                _uiState.value = NewsUiState.Success(news)
            } catch (e: Exception) {
                e.printStackTrace()  // Imprime el stack trace completo para depurar
                _uiState.value = NewsUiState.Error("Error al obtener noticias: ${e.localizedMessage}")
            }
        }
    }
}
