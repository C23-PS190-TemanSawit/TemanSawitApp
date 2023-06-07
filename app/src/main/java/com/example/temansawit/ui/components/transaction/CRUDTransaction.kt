package com.example.temansawit.ui.components.transaction

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.data.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CRUDTransaction(
    modalSheetState: ModalBottomSheetState,
    ) {
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
            0 -> Income(modalSheetState = modalSheetState)
            1 -> Outcome()
//            2 -> SettingsScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Income(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    modalSheetState: ModalBottomSheetState,
    ) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val userIdInput = viewModel.userIdState.observeAsState(initial = 0)
    val trxTimeInput = viewModel.tanggalTrx.observeAsState()
    val priceInput = viewModel.harga.observeAsState(initial = 0)
    val beratInput = viewModel.berat.observeAsState(initial = 0)
    val deskripsiInput = viewModel.deskripsi.observeAsState()
    val userId = userIdInput.value
    val trxTime = trxTimeInput.value.toString()
    val price = priceInput.value
    val berat = beratInput.value
    val deskripsi = deskripsiInput.value.toString()

    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = userId.toString(),
            label = {   Text(text = "UserId") },
            onValueChange = viewModel::onUserIdChange
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = trxTime,
            label = { Text(text = "tanggal transaksi") },
            onValueChange = viewModel::onTanggalTrxChange
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = price.toString(),
            label = { Text(text = "Harga /kg") },
            onValueChange = viewModel::onPriceChange
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = berat.toString(),
            label = { Text(text = "Berat Timbangan") },
            onValueChange = viewModel::onWeightChange
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = TextFieldValue(text = ((price) * (berat)).toString()),
            label = { Text(text = "Total Pemasukan") },
            onValueChange = { },
            enabled = false
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = deskripsi,
            label = { Text(text = "Total Pemasukan") },
            onValueChange = viewModel::onDescChange
        )

        Spacer(modifier = modifier.padding(16.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(100.dp)),
            onClick = {
                viewModel.createIncome(userId, trxTime, price, berat, deskripsi).observe(lifecycleOwner, { saveTrx ->
                    when (saveTrx) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            coroutineScope.launch {
                                    modalSheetState.hide()
                            }
                            Toast.makeText(context, "Berhasil Menambahkan Catatan Transaksi", Toast.LENGTH_LONG).show()
                        }
                        is Result.Error -> {}
                    }
                })
            }
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