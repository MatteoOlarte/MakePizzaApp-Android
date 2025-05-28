package com.example.makepizza_android.ui.view.screens.pizzapersonalizada

sealed class PizzaBuilderScreenViewState {
    object Success: PizzaBuilderScreenViewState()
    object Loading: PizzaBuilderScreenViewState()
    data class OnError(val err: String): PizzaBuilderScreenViewState()
}