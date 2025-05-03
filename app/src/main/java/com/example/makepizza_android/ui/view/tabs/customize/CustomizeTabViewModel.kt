package com.example.makepizza_android.ui.view.tabs.customize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.models.UserModel
import com.example.makepizza_android.data.repository.PizzaRepository
import com.example.makepizza_android.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomizeTabViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<UserModel?>()
    val currentUser: LiveData<UserModel?> = _currentUser

    private val _pizzas = MutableLiveData<List<PizzaListModel>>()
    val pizzas: LiveData<List<PizzaListModel>> = _pizzas

    private val _uiState = MutableStateFlow<CustomizeTabState>(CustomizeTabState.Loading)
    val uiState: StateFlow<CustomizeTabState> = _uiState.asStateFlow()

    val userRepository = UserRepository()

    val pizzaRepository = PizzaRepository()

    init {
        viewModelScope.launch { launchTasks() }
    }

    private suspend fun launchTasks() {
        fetchCurrentUserModel()
        fetchPizzasFromUser()
    }

    private suspend fun fetchCurrentUserModel() {
        _uiState.value = CustomizeTabState.Loading

        val user: UserModel? = userRepository.getCurrentUser()
        _currentUser.value = user
        _uiState.value = CustomizeTabState.Success(hasCurrentUser = user != null)
    }

    private suspend fun fetchPizzasFromUser() {
        if (currentUser.value == null) return

        _uiState.value = CustomizeTabState.Loading

        val pizzas = pizzaRepository.getAllPizzasFromUser()
        _pizzas.postValue(pizzas)
        _uiState.value = CustomizeTabState.Success(hasCurrentUser = true)
    }
}