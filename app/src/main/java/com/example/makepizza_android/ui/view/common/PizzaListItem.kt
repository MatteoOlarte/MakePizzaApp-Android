package com.example.makepizza_android.ui.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.makepizza_android.R
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PizzaListItem(
    pizzaModel: PizzaListModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val format = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(pizzaModel.price.times(1))

    Card(
        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
        shape = RoundedCornerShape(0.dp),
        onClick = onClick
    ) {
        Box(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ItemImage(pizzaModel.imageURL)

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${pizzaModel.name} ${pizzaModel.size}",
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

                    Text(
                        text = pizzaModel.desc ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Light
                        ),
                        maxLines = 2
                    )
                }
            }
        }
    }
}

@Composable
fun ItemImage(url: String?) {
    Surface(
        modifier = Modifier.height(56.dp).width(56.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        CoilImage(url)
    }
}

@Composable
fun PizzaListItemLoading(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer = rememberShimmer(ShimmerBounds.View)
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
        shape = RoundedCornerShape(0.dp),
    ) {
        Box(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface (
                    modifier = Modifier.height(56.dp).width(56.dp).shimmer(shimmerInstance),
                    shape = RoundedCornerShape(5.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {}

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface (
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            modifier = Modifier.height(16.dp).width(128.dp).shimmer(shimmerInstance)
                        ){  }
                        Surface (
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            modifier = Modifier.height(16.dp).width(25.dp).shimmer(shimmerInstance)
                        ){  }
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Surface (
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier.height(16.dp).width(128.dp).shimmer(shimmerInstance)
                    ){  }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPizzaItem() {
    val model = PizzaListModel(
        uid = "uid1",
        name = "namsadasdasdasdsadasdasdasdase",
        desc = "desc",
        price = 999.9f,
        size = "S",
        imageURL = null
    )
    ApplicationTheme { PizzaListItem(model, onClick = {}) }
}

@Preview
@Composable
private fun PreviewPizzaListItemLoading() {
    ApplicationTheme { PizzaListItemLoading() }
}