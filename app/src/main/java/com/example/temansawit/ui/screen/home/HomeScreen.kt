package com.example.temansawit.ui.screen.home

import BottomSheet
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
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.SectionText
import com.example.temansawit.ui.components.home.*
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.Typography
import com.example.temansawit.util.TransactionViewModel
import kotlinx.coroutines.launch

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
    BottomSheet(modalSheetState = modalSheetState) {
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
                HomeScreen(
                    navigateToDetail = { transactionId ->
                        navHostController.navigate(
                            Screen.DetailTransaction.createRoute(
                                transactionId
                            )
                        )
                    },
                    viewModel = transactionViewModel,
                    navHostController = navHostController,
                    modalSheetState = modalSheetState
                )
            }
        )
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
    ) {
        Column(
            modifier = Modifier
                        .verticalScroll(rememberScrollState())
        ) {
            Component1(navHostController = navHostController)
            Spacer(modifier = Modifier.padding(top = 56.dp))
            GrafikPendapatan(modalSheetState = modalSheetState)
            Chart()
            SectionText(title = stringResource(R.string.riwayat_transaksi))
            Box(modifier = Modifier
                .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
            ) {
                viewModel.income.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            viewModel.getIncome()
                        }
                        is UiState.Success -> {
                            Transaction(
                                listIncome = uiState.data,
                                modifier = modifier.padding(),
                                navigateToDetail = navigateToDetail
                            )
                        }
                        is UiState.Error -> {
                            Alert403(navHostController = navHostController)
                        }
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
                        Preferences.setLoggedIn(sharedPreferences, false)
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
    navHostController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("Temansawit Guest") }

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
            Welcome(name = name)
            viewModel.name.collectAsState(initial = UiState.Loading).value.let { user ->
                when (user) {
                    is UiState.Loading -> {
                        viewModel.getUserProfile()
                    }
                    is UiState.Success -> {
                        name = if (user.data.fullName != null) {
                            user.data.fullName.toString()
                        } else {
                            "User name null"
                        }
                    }
                    is UiState.Error -> {
                        Alert403(navHostController = navHostController)
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
                    text = "Rp 268.304.000",
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
    modalSheetState: ModalBottomSheetState,
    ) {
    val coroutineScope = rememberCoroutineScope()

    Row {
            Column(
                modifier = modifier
            ) {
                SectionText(title = stringResource(R.string.grafik_pendapatan))
                Text(
                    text = "Juli 2023",
                    style = Typography.body1,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(50),
                onClick = {
                    coroutineScope.launch {
                        if (modalSheetState.isVisible)
                                modalSheetState.hide()
                        else
                                modalSheetState.show()

                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "tambah transaksi")
                Text(text = "CATATAN BARU")
            }
        }
}

@Composable
fun Transaction(
    listIncome: List<IncomeResponseItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column(
//        modifier = modifier.fillMaxSize(),
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