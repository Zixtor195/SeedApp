package app.android.seedapp.utils.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import app.android.seedapp.R

@Composable
fun HeaderImageLogo(modifier: Modifier = Modifier) {
    val painter = painterResource(id = R.drawable.logo_seed_app)
    Image(
        painter = painter,
        colorFilter = ColorFilter.tint(color = Color.White),
        contentDescription = "AppImage",
        modifier = modifier
            .aspectRatio((painter.intrinsicSize.width * 2) / painter.intrinsicSize.height)
            .fillMaxWidth(),
        contentScale = ContentScale.FillHeight

    )
}