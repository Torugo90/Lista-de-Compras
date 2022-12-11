package com.torugo.lista.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.torugo.lista.R
import com.torugo.lista.ui.theme.ListaTheme

@Composable
fun IconImage(
    @DrawableRes iconLightId: Int,
    iconColor: Color = Color.Gray,
    backgroundColor: Color = Color.Green,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .size(38.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
    ) {
        Image(
            painter = painterResource(iconLightId),
            contentDescription = "",
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.Center)
                .clickable { onClick() },
            colorFilter = ColorFilter.tint(
                iconColor
            )
        )
    }
}

@Preview
@Composable
fun IconImageLightPreview() {
    ListaTheme {
        IconImage(
            R.drawable.ic_launcher_foreground
        )
    }
}
