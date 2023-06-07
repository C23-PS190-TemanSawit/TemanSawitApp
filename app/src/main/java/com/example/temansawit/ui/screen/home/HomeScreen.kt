package com.example.temansawit.ui.screen.home

import BottomSheet
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.temansawit.data.Result
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.SectionText
import com.example.temansawit.ui.components.home.CardTransaction
import com.example.temansawit.ui.components.home.Chart
import com.example.temansawit.ui.components.home.Pendapatan
import com.example.temansawit.ui.components.home.Welcome
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    navHostController: NavHostController = rememberNavController(),
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
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navigateToDetail: (Int) -> Unit,
    modalSheetState: ModalBottomSheetState,
    ) {
        Column(
            modifier = Modifier
                        .verticalScroll(rememberScrollState())
        ) {
            Component1()
            Spacer(modifier = Modifier.padding(top = 56.dp))
            GrafikPendapatan(modalSheetState = modalSheetState)
            Chart()
            SectionText(title = stringResource(R.string.riwayat_transaksi))
            Box(modifier = Modifier
                .padding(bottom = 36.dp, start = 16.dp, end = 16.dp)
            ) {
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
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
                        is UiState.Error -> {}
                    }
                }
            }
        }
}

@Composable
fun Component1(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var name by remember { mutableStateOf("y") }

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
            
            LaunchedEffect(Unit) {
//                viewModel.getNewToken().observe(lifecycleOwner, {token ->
//                    when (token) {
//                        is Result.Loading -> {}
//                        is Result.Success -> {
//                            val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
//                            Preferences.newToken(token.data.accessToken, sharedPreferences)
//                        }
//                        is Result.Error -> {}
//                    }
//                })

                viewModel.getUserProfile().observe(lifecycleOwner, { user ->
                    when (user) {
                        is Result.Loading -> {

                        }
                        is Result.Success -> {
                            if (user.data.fullName != null) {
                                name = user.data.fullName.toString()
                            } else {
                                name = "bau"
                            }
                        }
                        is Result.Error -> {
                            name = "Temansawit User"
                        }
                    }
                })
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .clip(shape = RoundedCornerShape(100.dp)),
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
                Text(text = "TRANSAKSI BARU")
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
        listIncome.forEach() {
            CardTransaction(
                berat = it.totalWeight,
                total = it.price * it.totalWeight,
                tanggal = it.updatedAt,
                tint = Color.Green,
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