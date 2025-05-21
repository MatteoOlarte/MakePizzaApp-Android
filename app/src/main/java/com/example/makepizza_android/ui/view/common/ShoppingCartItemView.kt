package com.example.makepizza_android.ui.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.makepizza_android.domain.models.Cart
import com.example.makepizza_android.domain.models.CartItem
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ShoppingCartItemView(
    data: Cart,
    checked: Boolean,
    onCheckedChange: (value: Boolean) -> Unit,
    onDeleteClick: (item: Cart) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
        modifier = modifier,
        shape = RoundedCornerShape(0.dp)
    ) {
        ItemContent(data, checked, onCheckedChange, onDeleteClick)
    }
}

@Composable
private fun ItemContent(
    data: Cart,
    checked: Boolean,
    onCheckedChange: (value: Boolean) -> Unit,
    onDeleteClick: (item: Cart) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) }
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            ItemNameAndPrice(data.item)
            ItemDescription(data.item)
        }

        IconButton(
            onClick = { onDeleteClick(data) }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "remove this item from cart"
            )
        }
    }
}

@Composable
private fun ItemNameAndPrice(data: CartItem) {
    val format = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(data.price.times(1))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = data.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        Text(
            text = "$$format",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ItemDescription(data: CartItem) {
    Text(
        text = data.desc ?: "",
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Light
        ),
        maxLines = 2
    )
}
