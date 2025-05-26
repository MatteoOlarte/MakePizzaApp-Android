package com.example.makepizza_android.ui.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.makepizza_android.R

@Composable
fun CoilImage(url: String?) {
    val fuc = "1DgGBTedKKCGsI0Ksb5ZnG5Oo2T_xPXy3"

    if (url == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "contentDescription",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        print(url)
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}