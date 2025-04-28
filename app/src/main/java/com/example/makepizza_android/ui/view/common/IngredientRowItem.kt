package com.example.makepizza_android.ui.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.makepizza_android.R
import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.ui.theme.ApplicationTheme
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun IngredientRowItem(ingredient: IngredientListModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(0.dp).height(120.dp).width(120.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {}
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "contentDescription",
                contentScale = ContentScale.Companion.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BackGradient(
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun IngredientLoadingItem(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer = rememberShimmer(ShimmerBounds.View)
) {
    Surface(
        modifier = modifier.padding(0.dp).height(120.dp).width(120.dp).shimmer(shimmerInstance),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Surface(
                modifier = Modifier.height(16.dp).width(70.dp).shimmer(shimmerInstance),
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {}
        }
    }
}


@Preview
@Composable
fun PreviewIngredientItem() {
    val item = IngredientListModel(
        "uuid",
        "name",
        "desc",
        999.9f,
        true
    )
    ApplicationTheme { IngredientRowItem(item) }
}

@Preview
@Composable
fun PreviewIngredientLoadingItem() {
    ApplicationTheme { IngredientLoadingItem() }
}