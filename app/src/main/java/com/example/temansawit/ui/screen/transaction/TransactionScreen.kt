package com.example.temansawit.ui.screen.transaction

import BottomSheet
import BottomSheetType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.util.TransactionViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: TransactionViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navigateIncomeDetail: (Int) -> Unit,
    navigateOutcomeDetail: (Int) -> Unit
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedBottomSheet by remember { mutableStateOf(BottomSheetType.None) }

    BottomSheet(
        modalSheetState = modalSheetState,
        selectedBottomSheet = selectedBottomSheet,
        onBottomSheetSelected = { sheetType ->
            selectedBottomSheet = sheetType
            coroutineScope.launch {
                modalSheetState.show()
            }
        }
    ) {
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
                        onClick = {
                            selectedBottomSheet = BottomSheetType.Camera
                            coroutineScope.launch {
                                modalSheetState.show()
                            }
                        },
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
                    viewModel.combinedResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                viewModel.fetchCombinedResponse()
                            }
                            is UiState.Success -> {
                                val sortedIncome = uiState.data.incomeItems.sortedBy { it.transactionTime }
                                val sortedOutcome = uiState.data.outcomeItems.sortedBy { it.transactionTime }

                                if (uiState.data.incomeItems.isNotEmpty() || uiState.data.outcomeItems.isNotEmpty()) {
                                    IncomeData(
                                        listIncome = sortedIncome,
                                        modifier = modifier.padding(),
                                        navigateToDetail = navigateIncomeDetail
                                    )
                                    OutcomeData(
                                        lisOutcome = sortedOutcome,
                                        navigateToDetail = navigateOutcomeDetail
                                    )
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        Text(
                                            text = "Tidak ada data",
                                        )
                                    }
                                }
                            }
                            is UiState.Error -> {}
                        }
                    }
//                    viewModel.outcome.collectAsState(initial = UiState.Loading).value.let { outcome ->
//                        when (outcome) {
//                            is UiState.Loading -> {
//                                viewModel.getOutcome()
//                            }
//                            is UiState.Success -> {
//                                OutcomeData(
//                                    lisOutcome = outcome.data,
//                                    navigateToDetail = navigateOutcomeDetail
//                                )
//                            }
//                            is UiState.Error -> {}
//                        }
//                    }
                }
            }
        )
    }
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
                tanggal = it.transactionTime,
                modifier = modifier.clickable{ navigateToDetail(it.incomeId) }
            )
        }
    }
}
@Composable
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
                description = it.description,
                modifier = modifier.clickable{ navigateToDetail(it.outcomeId) }
            )
        }
    }
}