package com.example.temansawit.ui.screen.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.di.Injection
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.ui.screen.home.Transaction
import com.example.temansawit.ui.theme.Green700

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
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
                modifier = modifier.fillMaxSize(),
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
    )
}