package com.example.temansawit.ui.components.transaction

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.data.Result
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.util.TransactionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

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
            1 -> Outcome(modalSheetState = modalSheetState)
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
    viewModel2: TransactionViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    modalSheetState: ModalBottomSheetState,
    ) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val trxTimeInput = viewModel.tanggalTrx.observeAsState()
    val priceInput = viewModel.harga.observeAsState(initial = 0)
    val beratInput = viewModel.berat.observeAsState(initial = 0)
    val deskripsiInput = viewModel.deskripsi.observeAsState()
    val trxTime = trxTimeInput.value.toString()
    val price = priceInput.value
    val berat = beratInput.value
    val deskripsi = deskripsiInput.value.toString()
    val selectedDate = remember { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableStateOf(LocalDate.now())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    }

    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val year = selectedDate.value.year
                    val month = selectedDate.value.monthValue - 1
                    val day = selectedDate.value.dayOfMonth

                    val datePicker = DatePickerDialog(
                        context,
                        { _, yearSelected, monthOfYear, dayOfMonth ->
                            selectedDate.value =
                                LocalDate.of(yearSelected, monthOfYear + 1, dayOfMonth)
                        },
                        year,
                        month,
                        day
                    )

                    datePicker.show()
                },
            value = trxTime,
            label = { Text(text = "Tanggal Transaksi (YYYY-MM-DD)") },
            onValueChange = { selectedDate.value.toString() },
            readOnly = true
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = price.toString(),
            label = { Text(text = "Harga /kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = viewModel::onPriceChange
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = berat.toString(),
            label = { Text(text = "Berat Timbangan (Kilogram)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = viewModel::onWeightChange
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = TextFieldValue(text = ((price) * (berat)).toString()),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Total Pemasukan") },
            onValueChange = { },
            enabled = false
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = deskripsi,
            label = { Text(text = "Deskripsi (opsional)") },
            onValueChange = viewModel::onDescChange
        )

        Spacer(modifier = modifier.padding(16.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(100.dp)),
            onClick = {
                viewModel.createIncome(trxTime, price, berat, deskripsi).observe(lifecycleOwner) { saveTrx ->
                    when (saveTrx) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                            Toast.makeText(context, saveTrx.data.message, Toast.LENGTH_LONG).show()
                            viewModel2.fetchCombinedResponse()
                        }
                        is Result.Error -> {
                            Toast.makeText(
                                context,
                                "Gagal, masukkan data dengan benar",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {}
                    }
                }
            }
        ) {
            Text(text = "Simpan Transaksi")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Outcome(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    viewModel2: TransactionViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    modalSheetState: ModalBottomSheetState,
    ) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val trxTimeInput = viewModel.tanggalTrx.observeAsState()
    val totalOutcomeInput = viewModel.totalOutcome.observeAsState(initial = 0)
    val deskripsiInput = viewModel.deskripsi.observeAsState(initial = "")
    val trxTime = trxTimeInput.value.toString()
    val totalOutcome = totalOutcomeInput.value
    val deskripsi = deskripsiInput.value
    val selectedDate = remember { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableStateOf(LocalDate.now())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    }

    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val year = selectedDate.value.year
                    val month = selectedDate.value.monthValue - 1
                    val day = selectedDate.value.dayOfMonth

                    val datePicker = DatePickerDialog(
                        context,
                        { _, yearSelected, monthOfYear, dayOfMonth ->
                            selectedDate.value =
                                LocalDate.of(yearSelected, monthOfYear + 1, dayOfMonth)
                        },
                        year,
                        month,
                        day
                    )

                    datePicker.show()
                },
            value = trxTime,
            label = { Text(text = "Tanggal Transaksi (YYYY-MM-DD)") },
            onValueChange = { selectedDate.value.toString() },
            readOnly = true
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = totalOutcome.toString(),
            label = { Text(text = "Total Pengeluaran") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = viewModel::onTotalOutcomeChange
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = deskripsi,
            label = { Text(text = "Deskripsi") },
            onValueChange = viewModel::onDescChange,
        )

        Spacer(modifier = modifier.padding(16.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(100.dp)),
            onClick = {
                viewModel.createOutcome(trxTime, totalOutcome, deskripsi).observe(lifecycleOwner) { saveTrx ->
                    when (saveTrx) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                            Toast.makeText(context, saveTrx.data.message, Toast.LENGTH_LONG).show()
                            viewModel2.fetchCombinedResponse()
                        }
                        is Result.Error -> {
                            Toast.makeText(
                                context,
                                "Gagal, masukkan data dengan benar",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {}
                    }
                }
            }
        ) {
            Text(text = "Simpan Transaksi")
        }
    }
}