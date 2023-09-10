package com.example.temansawit.ui.screen.transaction

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.temansawit.R
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.transaction.CardIncomeDetail
import com.example.temansawit.ui.components.transaction.CardNoTransaksi
import com.example.temansawit.ui.components.transaction.CardOutcomeDetail
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.data.Result
import com.example.temansawit.ui.navigation.Screen

@Composable
fun DetailTrxScreen(
    trxId: Int,
    viewmodel: TransactiomViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navigateBack: () -> Unit,
    navHostController: NavHostController,
    ) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick =  navigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_before_24),
                            contentDescription = "Kembali",
                            Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    // Add delete icon action
                    IconButton(onClick = {
                        viewmodel.deleteIncome(trxId).observe(lifecycleOwner) {
                            when (it) {
                                is Result.Loading -> {}
                                is Result.Success -> {
                                    Toast.makeText(context, "Sukses menghapus data", Toast.LENGTH_SHORT).show()
                                    navHostController.navigate(Screen.Home.route)
                                }
                                is Result.Error -> {
                                    Toast.makeText(context, "Gagal, silahkan cek koneksi anda", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
//                        navHostController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            Modifier.size(32.dp)
                        )
                    }
                },
                backgroundColor = GreenPressed,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(GreenPrimary.copy(alpha = 0.2F))
        ) {
            Column(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            GreenPressed,
                            RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        )
                ) {
                    CardNoTransaksi(
                        painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                        tint = GreenPrimary,
                        jenis_transaksi = "Transaksi Masuk"
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    viewmodel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                viewmodel.getIncomeById(trxId)
                            }
                            is UiState.Success -> {
                                CardIncomeDetail(
                                    detailTrx = uiState.data
                                )
                            }
                            is UiState.Error -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailOutcomeScreen(
    trxId: Int,
    viewmodel: TransactiomViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navigateBack: () -> Unit,
    navHostController: NavHostController
    ) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick =  navigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_before_24),
                            contentDescription = "Kembali",
                            Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    // Add delete icon action
                    IconButton(onClick = {
                        viewmodel.deleteOutcome(trxId).observe(lifecycleOwner) {
                            when (it) {
                                is Result.Loading -> {}
                                is Result.Success -> {
                                    Toast.makeText(context, "Sukses menghapus data", Toast.LENGTH_SHORT).show()
                                    navHostController.navigate(Screen.Home.route)
                                }
                                is Result.Error -> {
                                    Toast.makeText(context, "Gagal, silahkan cek koneksi anda", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
//                        navHostController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            Modifier.size(32.dp)
                        )
                    }
                },
                backgroundColor = GreenPressed,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(GreenPrimary.copy(alpha = 0.2F))
        ) {
            Column(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            GreenPressed,
                            RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        )
                ) {
                    CardNoTransaksi(
                        painter = painterResource(id = R.drawable.baseline_arrow_circle_up_24),
                        tint = Color.Red,
                        jenis_transaksi = "Transaksi Keluar"
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    viewmodel.outcomeById.collectAsState().value.let { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {
                                viewmodel.getOutcomeById(trxId)
                            }
                            is UiState.Success -> {
                                CardOutcomeDetail(
                                    detailTrx = uiState.data
                                )
                            }
                            is UiState.Error -> {}
                        }
                    }
                }
            }
        }
    }
}
