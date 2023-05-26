package com.example.temansawit.ui.screen.faq

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FaqScreen(
    modifier: Modifier = Modifier
) {
    val items = listOf("Item 1", "Item 2", "Item 3")

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ListItem(
                    modifier = Modifier.padding(8.dp),
                    text = { Text(text = item) }
                )
            }
        }
    }
}
