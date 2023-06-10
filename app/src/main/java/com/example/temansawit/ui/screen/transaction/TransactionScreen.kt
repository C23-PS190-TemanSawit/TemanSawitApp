package com.example.temansawit.ui.screen.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.network.response.OutcomeResponseItem
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.home.IncomeCard
import com.example.temansawit.ui.components.home.OutcomeCard
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.util.TransactionViewModel

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: TransactiomViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    viewModel2: TransactionViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),

    navigateToDetail: (Int) -> Unit
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ScaffoldApp(
        modifier = modifier.padding(vertical = 36.dp),
        bottomBar = {
            if (currentRoute != Screen.DetailTransaction.route) {
                BottomBar(navHostController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (currentRoute != Screen.DetailTransaction.route) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { },
                    backgroundColor = Green700,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_camera_alt_24),
                        contentDescription = "Deteksi Sawit"
                    )
                }
            }
        },
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                    viewModel2.income.collectAsState(initial = UiState.Loading).value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                viewModel2.getIncome()
                            }
                            is UiState.Success -> {
                                IncomeData(
                                    listIncome = uiState.data,
                                    modifier = modifier.padding(),
                                    navigateToDetail = navigateToDetail
                                )
                            }
                            is UiState.Error -> {}
                        }
                    }
                    viewModel.outcome.collectAsState(initial = UiState.Loading).value.let { outcome ->
                        when (outcome) {
                            is UiState.Loading -> { viewModel.getOutcome() }
                            is UiState.Success -> {
                                OutcomeData(
                                    lisOutcome = outcome.data,
                                    navigateToDetail = navigateToDetail
                                )
                            }
                            is UiState.Error -> {}
                        }
                    }
            }
        }
    )
}

@Composable
fun IncomeData(
    listIncome: List<IncomeResponseItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listIncome.forEach {
            IncomeCard(
                berat = it.totalWeight,
                total = it.price * it.totalWeight,
                tanggal = it.updatedAt,
                modifier = modifier.clickable{ navigateToDetail(it.incomeId) }
            )
        }
    }

}@Composable
fun OutcomeData(
    lisOutcome: List<OutcomeResponseItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        lisOutcome.forEach {
            OutcomeCard(
                total_outcome = it.total_outcome,
                tanggal = it.transactionTime,
                description = it.description
            )
        }
    }
}