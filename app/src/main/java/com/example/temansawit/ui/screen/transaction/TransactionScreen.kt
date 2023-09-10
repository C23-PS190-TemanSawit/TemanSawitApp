package com.example.temansawit.ui.screen.transaction

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.temansawit.ui.components.home.BottomSheet
import com.example.temansawit.ui.components.home.BottomSheetType
import com.example.temansawit.ui.components.home.IncomeCard
import com.example.temansawit.ui.components.home.OutcomeCard
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.components.transaction.FilterChipsRow
import com.example.temansawit.ui.components.transaction.SearchMenu
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.util.TransactionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
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
    // Add a mutable state for the user's search query
    val searchQuery = remember { mutableStateOf("") }

    // Add a mutable state for the selected filters
    val selectedFilters = remember { mutableStateListOf<String>() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        viewModel.fetchCombinedResponse()
    }


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
            modifier = modifier.padding(top = 26.dp),
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
                        .padding(start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    SearchMenu(
                        onQueryChange = { query->
                            searchQuery.value = query
                            Log.d("testtt", searchQuery.value)
                        },
                        onSearchSubmit = {
                            // Hide the keyboard after the user presses Enter
                            keyboardController?.hide()
                            // Clear focus to dismiss the focus from the TextField
                            focusManager.clearFocus()
                        },
                        navController = navHostController
                    )

                    // Add FilterChips for different disasters
                    FilterChipsRow(selectedFilters) { selectedFilter ->
                        if (selectedFilters.contains(selectedFilter)) {
                            selectedFilters.remove(selectedFilter)
                        } else {
                            selectedFilters.add(selectedFilter)
                        }
                    }
                    viewModel.combinedResponse.collectAsState().value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(28.dp),
                                        color = GreenPrimary
                                    )
                                }
                            }
                            is UiState.Success -> {
                                val mergedList = (uiState.data.incomeItems + uiState.data.outcomeItems)
                                    .sortedBy { it.transactionTime }
                                    .reversed()

                                // Filtering logic
                                val filteredList = when {
                                    selectedFilters.isEmpty() -> mergedList // No filters selected, show all items
                                    selectedFilters.contains("Pemasukan") && selectedFilters.contains("Pengeluaran") -> mergedList
                                    selectedFilters.contains("Pemasukan") -> mergedList.filterIsInstance<IncomeResponseItem>()
                                    selectedFilters.contains("Pengeluaran") -> mergedList.filterIsInstance<OutcomeResponseItem>()
                                    else -> emptyList() // No matching filters, show nothing
                                }
                                val filteredAndSearchedList = filteredList
                                    .filter { item ->
                                        // Apply search query filtering
//                                        item.description.contains(searchQuery.value, ignoreCase = true)
                                        item.transactionTime.contains(searchQuery.value, ignoreCase = true)
                                    }

                                if (filteredAndSearchedList.isNotEmpty()) {
                                    Column {
                                        filteredAndSearchedList.forEach { item ->
                                            when (item) {
                                                is IncomeResponseItem -> {
                                                    // Display IncomeData UI for income item
                                                    IncomeData(
                                                        listIncome = listOf(item),
                                                        modifier = modifier.padding(),
                                                        navigateToDetail = navigateIncomeDetail
                                                    )
                                                }
                                                is OutcomeResponseItem -> {
                                                    // Display OutcomeData UI for outcome item
                                                    OutcomeData(
                                                        lisOutcome = listOf(item),
                                                        modifier = modifier.padding(),
                                                        navigateToDetail = navigateOutcomeDetail
                                                    )
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "Tidak ada data")
                                    }
                                }
                            }
                            is UiState.Error -> {}
                        }
                    }
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
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
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