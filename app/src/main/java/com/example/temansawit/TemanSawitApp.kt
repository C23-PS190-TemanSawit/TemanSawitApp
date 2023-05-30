package com.example.temansawit

import BottomSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.temansawit.ui.components.home.Pendapatan
import com.example.temansawit.ui.components.home.Welcome
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.faq.FaqScreen
import com.example.temansawit.ui.screen.home.HomeScreen
import com.example.temansawit.ui.screen.profile.ProfileScreen
import com.example.temansawit.ui.screen.transaction.DetailTrxScreen
import com.example.temansawit.ui.screen.transaction.TransactionScreen
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TemanSawitApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    ) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    BottomSheet(modalSheetState = modalSheetState) {
        Scaffold(
            bottomBar = {
                if (currentRoute != Screen.DetailTransaction.route) {
                    BottomBar(navHostController)
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            scaffoldState = rememberScaffoldState(),
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
            content = { it ->
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.Home.route,
                    modifier = modifier.padding(it)
                ) {
                    composable(Screen.Home.route) {
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
                    composable(Screen.Transaction.route) {
                        TransactionScreen(
                            navigateToDetail = { transactionId ->
                                navHostController.navigate(
                                    Screen.DetailTransaction.createRoute(
                                        transactionId
                                    )
                                )
                            }
                        )
                    }
                    composable(Screen.Faq.route) {
                        FaqScreen()
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen()
                    }
                    composable(
                        route = Screen.DetailTransaction.route,
                        arguments = listOf(navArgument("transactionId") {
                            type = NavType.LongType
                        }),
                    ) {
                        val id = it.arguments?.getLong("transactionId") ?: -1L
                        DetailTrxScreen(
                            trxId = id,
                            navigateBack = { navHostController.navigateUp() },
                        )
                    }
                }

            }
        )
    }
}