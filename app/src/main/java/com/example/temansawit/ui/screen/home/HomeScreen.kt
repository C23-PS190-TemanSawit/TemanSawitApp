package com.example.temansawit.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.Component1
import com.example.temansawit.di.Injection
import com.example.temansawit.model.Trx
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.home.CardTransaction
import com.example.temansawit.ui.screen.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
//                        .verticalScroll(rememberScrollState())
        ) {
            Component1()
            Spacer(modifier = Modifier.padding(top = 100.dp))
            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getAllTrx()
                        }
                        is UiState.Success -> {
                            Transaction(
                                listTransaction = uiState.data,
                                modifier = modifier,
                                navigateToDetail = navigateToDetail
                            )
                        }
                        is UiState.Error -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun Transaction(
    listTransaction: List<Trx>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTransaction, key = { it.cardTransaction.id }) { trx ->
            CardTransaction(
                berat = trx.cardTransaction.berat,
                total = trx.cardTransaction.total,
                tanggal = trx.cardTransaction.tanggal,
                tint = trx.cardTransaction.tint,
                modifier = modifier.clickable{ navigateToDetail(trx.cardTransaction.id) }
            )
        }
    }
//    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        LocalDateTime.now()
//    } else {
//        TODO("VERSION.SDK_INT < O")
//    }
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val formatted = current.format(formatter)
//    CardTransaction(
//        transaction =
//    )
}