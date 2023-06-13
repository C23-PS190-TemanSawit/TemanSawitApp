package com.example.temansawit.ui.screen.home

import BottomSheet
import BottomSheetType
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.di.Preferences
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.network.response.OutcomeResponseItem
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.SectionText
import com.example.temansawit.ui.components.home.*
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.transaction.IncomeData
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.Typography
import com.example.temansawit.util.TransactionViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    navHostController: NavHostController = rememberNavController(),
    transactionViewModel: TransactionViewModel
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
                    HomeScreen(
                        navigateToDetail = { transactionId ->
                            navHostController.navigate(
                                Screen.DetailTransaction.createRoute(
                                    transactionId
                                ),
                            )
                        },
                        viewModel = transactionViewModel,
                        navHostController = navHostController,
                        modalSheetState = modalSheetState,
                        onClick = {
                            selectedBottomSheet = BottomSheetType.CRUDTransaction
                            coroutineScope.launch {
                                if (modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.show()

                            }
                        }
                    )
                }
            )
//        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navHostController: NavHostController,
    navigateToDetail: (Int) -> Unit,
    modalSheetState: ModalBottomSheetState,
    onClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                        .verticalScroll(rememberScrollState())
        ) {
            viewModel.combinedResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.fetchCombinedResponse()
                    }
                    is UiState.Success -> {
                        Component1(
                            navHostController = navHostController,
                            listIncome = uiState.data.incomeItems,
                            listOutcome = uiState.data.outcomeItems
                        )
                        Spacer(modifier = Modifier.padding(top = 56.dp))
                        GrafikPendapatan(onClick = onClick)
                        Chart(
                            listIncome = uiState.data.incomeItems
                        )
                        SectionText(title = stringResource(R.string.riwayat_transaksi))
                        Box(modifier = Modifier
                            .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
                        ) {
                            IncomeData(
                                listIncome = uiState.data.incomeItems.takeLast(2),
                                modifier = modifier.padding(),
                                navigateToDetail = navigateToDetail
                            )
//                            OutcomeData(
//                                lisOutcome = uiState.data.outcomeItems,
//                                modifier = modifier.padding(),
//                                navigateToDetail = navigateToDetail
//                            )

                        }
                    }
                    is UiState.Error -> {
                        Alert403(navHostController = navHostController)
                    }
                }
            }
        }
}

@Composable
fun Alert403(navHostController: NavHostController) {
    Column() {
        val openDialog = remember { mutableStateOf(true)  }
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = true
            },
            title = {
                Text(text = "Sesi anda telah berakhir")
            },
            text = {
                Text("Silahkan login ulang untuk melanjutkan")
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                        Preferences.logoutUser(sharedPreferences)
                        navHostController.popBackStack()
                        navHostController.navigate("loginScreen")
                    }) {
                    Text("Oke")
                }
            },
        )
    }
}

@Composable
fun Component1(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    listIncome: List<IncomeResponseItem>,
    listOutcome: List<OutcomeResponseItem>,
    navHostController: NavHostController
) {
    var name by remember { mutableStateOf("Temansawit Guest") }
    var imageUser by remember { mutableStateOf("https://www.citypng.com/public/uploads/preview/free-round-flat-male-portrait-avatar-user-icon-png-11639648873oplfof4loj.png") }
    val sumIncome = listIncome.sumOf { it.price * it.totalWeight }
    val sumOutcome = listOutcome.sumOf { it.total_outcome }
    val total = (sumIncome - sumOutcome)
    val totalIncomeWithFormat = String.format("%,d", total).replace(",", ".")

    Pendapatan(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(184.dp)
                .background(
                    GreenPressed,
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
        ) {
            Welcome(
                name = name,
                imageUser = imageUser
            )
            viewModel.name.collectAsState(initial = UiState.Loading).value.let { user ->
                when (user) {
                    is UiState.Loading -> {
                        viewModel.getUserProfile()
                    }
                    is UiState.Success -> {
                        name = user.data.username.toString()
                        if (user.data.image != null ) {
                            imageUser = user.data.image.toString()
                        }
                    }
                    is UiState.Error -> {
                        Alert403(navHostController = navHostController)
                        name = "Temansawit Guest"
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(97.dp)
                .background(GreenSurface, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = "Pendapatan Anda",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Rp ${totalIncomeWithFormat}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GrafikPendapatan(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    ) {

    Row {
            SectionText(
                title = stringResource(R.string.grafik_pendapatan),

                modifier = Modifier.padding(top = 10.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(50),
                onClick = { onClick() },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "tambah transaksi")
                Text(text = "CATATAN BARU")
            }
        }
}