package com.example.temansawit.ui.components.transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ChipDefaults.filterChipColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.temansawit.R

@Composable
fun SearchMenu(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(50),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
//                focusedContainerColor = MaterialTheme.colorScheme.surface,
//                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
//                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(text = "Cari Transaksi")
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    // Call the onSearchSubmit callback when the user presses Enter
                    onSearchSubmit()
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Set the IME action to Done
            )
        )
        onQueryChange(query)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChipsRow(
    selectedFilters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        val filters = listOf("Pemasukan", "Pengeluaran") // Add more filters if needed
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 8.dp),
                selected = selectedFilters.contains(filter),
                onClick = { onFilterSelected(filter) },
                content = { Text(text = filter) },
                colors = if (selectedFilters.contains(filter)) {
                    filterChipColors(backgroundColor = Color.Green)
                } else {
                    filterChipColors(backgroundColor = Color.White)
                },
                border = BorderStroke(1.dp, Color.Gray)
            )
        }
    }
}