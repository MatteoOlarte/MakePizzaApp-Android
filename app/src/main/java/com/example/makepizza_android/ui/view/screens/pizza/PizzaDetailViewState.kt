package com.example.makepizza_android.ui.view.screens.pizza

sealed class PizzaDetailViewState {
    object Loading: PizzaDetailViewState()
    object Success: PizzaDetailViewState()
    object Error: PizzaDetailViewState()
    object CartClicked: PizzaDetailViewState()
}