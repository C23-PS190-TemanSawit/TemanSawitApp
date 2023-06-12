package com.example.temansawit.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.temansawit.ui.theme.Typography

@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = Typography.subtitle2,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
    )
}