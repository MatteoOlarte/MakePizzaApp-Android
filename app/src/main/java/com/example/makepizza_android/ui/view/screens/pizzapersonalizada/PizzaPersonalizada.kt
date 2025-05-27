package com.example.makepizza_android.ui.view.screens.pizzapersonalizada

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow

class PizzaPersonalizada : Screen {
    @Composable
    override fun Content() {
        PizzaPersonalizadaScreen()
    }
    @Composable
    fun PizzaPersonalizadaScreen() {
        var selectedSize by remember { mutableStateOf("Personal") }
        var selectedCrust by remember { mutableStateOf("") }
        var selectedSauce by remember { mutableStateOf("") }
        val selectedIngredients = remember { mutableStateListOf<String>() }

        val sizes = listOf("Personal", "Mediana", "Grande", "Familiar")
        val crusts = listOf("Delgada", "Gruesa", "Rellena")
        val sauces = listOf("Tomate", "Barbacoa", "Blanca")
        val ingredients = listOf(
            "Jamon", "Pepperoni", "Piña", "Pollo", "Champiñones",
            "Pimentón", "Cebolla", "Maíz", "Carne", "Tocino",
            "Albahaca", "Aceitunas"
        )

        val basePrice = 45.000

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Perzonaliza tu pizza",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tamaño", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sizes.forEach { size ->
                    FilterChip(
                        selected = selectedSize == size,
                        onClick = { selectedSize = size },
                        label = { Text(size) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Masa", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuField(
                options = crusts,
                selectedOption = selectedCrust,
                onOptionSelected = { selectedCrust = it },
                label = "Elige una opción"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Salsa", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuField(
                options = sauces,
                selectedOption = selectedSauce,
                onOptionSelected = { selectedSauce = it },
                label = "Elige una opción"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Ingredientes", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ingredients.forEach { ingredient ->
                    FilterChip(
                        selected = ingredient in selectedIngredients,
                        onClick = {
                            if (ingredient in selectedIngredients)
                                selectedIngredients.remove(ingredient)
                            else
                                selectedIngredients.add(ingredient)
                        },
                        label = { Text(ingredient) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Precio: ${String.format("%.3f", basePrice)}$",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = {
                   
                }) {
                    Text("Añadir")
                }
            }
        }
    }
}