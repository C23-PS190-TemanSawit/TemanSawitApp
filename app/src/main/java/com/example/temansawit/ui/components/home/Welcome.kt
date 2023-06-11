package com.example.temansawit.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun Welcome(
    name: String,
    imageUser: String
) {
    val imagePainter = rememberImagePainter(
        data = imageUser,
        builder = {
            transformations(CircleCropTransformation())
        }
    )
    Row(
        modifier = Modifier
            .padding(top = 40.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .width(70.dp)
                .height(70.dp),
            painter = imagePainter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Selamat Datang",
                color = Color(0xFFC1C9BE),
                fontSize = 14.sp,
            )
            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp)
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifikasi",
            tint = Color.White
        )
    }
}


@Composable
fun Pendapatan(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        val height = maxOf(largePlaceable.height, smallPlaceable.height)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            largePlaceable.placeRelative(0, 0)
            smallPlaceable.placeRelative(
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = height - smallPlaceable.height / 2
            )
        }
    }
}