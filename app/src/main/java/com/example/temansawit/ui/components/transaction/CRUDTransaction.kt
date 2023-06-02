package com.example.temansawit.ui.components.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CRUDTransaction() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Pendapatan", "Pengeluaran")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> Income()
            1 -> Outcome()
//            2 -> SettingsScreen()
        }
    }
}

@Composable
fun Income(modifier: Modifier = Modifier) {
    var income by remember{ mutableStateOf(TextFieldValue(""))}
    var income2 by remember{ mutableStateOf(TextFieldValue(""))}
    var total = 0
    
    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = income,
            label = { Text(text = "Harga /kg") },
            onValueChange = { income = it }
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = income2,
            label = { Text(text = "Berat Timbangan") },
            onValueChange = { income2 = it }
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = TextFieldValue(text = ((income.text.toIntOrNull() ?: 0) * (income2.text.toIntOrNull() ?: 0)).toString()),
            label = { Text(text = "Total Pemasukan") },
            onValueChange = { },
            enabled = false
        )
        Spacer(modifier = modifier.padding(26.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(100.dp)),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Simpan Transaksi")
        }
    }
}

@Composable
fun Outcome(modifier: Modifier = Modifier) {
    var outcome by remember{ mutableStateOf(TextFieldValue(""))}
    var outcome2 by remember{ mutableStateOf(TextFieldValue(""))}

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = outcome,
            label = { Text(text = "Harga /kg") },
            onValueChange = { outcome = it }
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = outcome2,
            label = { Text(text = "Berat Timbangan") },
            onValueChange = { outcome2 = it }
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = TextFieldValue(text = ((outcome.text.toIntOrNull() ?: 0) * (outcome2.text.toIntOrNull() ?: 0)).toString()),
            label = { Text(text = "Total Pemasukan") },
            onValueChange = { },
            enabled = false
        )
        Spacer(modifier = modifier.padding(26.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(100.dp)),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Simpan Transaksi")
        }
    }
}