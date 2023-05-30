package com.example.temansawit.ui.screen.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.di.Injection
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.ui.screen.home.Transaction

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    Scaffold(modifier = modifier.padding(vertical = 36.dp)) { it ->
        Column(
            modifier = modifier.fillMaxSize()
                .padding(it),
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
            ) {
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getAllTrx()
                        }
                        is UiState.Success -> {
                            Transaction(
                                listTransaction = uiState.data,
                                modifier = modifier.padding(),
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