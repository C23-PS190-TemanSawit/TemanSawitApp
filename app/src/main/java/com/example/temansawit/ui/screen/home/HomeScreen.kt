package com.example.temansawit.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temansawit.R
import com.example.temansawit.di.Injection
import com.example.temansawit.model.Trx
import com.example.temansawit.ui.common.UiState
import com.example.temansawit.ui.components.SectionText
import com.example.temansawit.ui.components.home.CardTransaction
import com.example.temansawit.ui.components.home.Chart
import com.example.temansawit.ui.components.home.Pendapatan
import com.example.temansawit.ui.components.home.Welcome
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
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
//            Image(
//                modifier = modifier
//                    .padding(horizontal = 16.dp),
//                painter = painterResource(id = R.drawable.eample_chart),
//                contentDescription = null
//            )
            SectionText(title = stringResource(R.string.riwayat_transaksi))
            Box(modifier = Modifier
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

@Composable
fun Component1() {
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
            Welcome(name = "Ucup")
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
                            modalSheetState.show()
                        else
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
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
    listTransaction: List<Trx>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    Column(
//        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        items(listTransaction, key = { it.cardTransaction.id }) { trx ->
            listTransaction.forEach { trx ->
                CardTransaction(
                    berat = trx.cardTransaction.berat,
                    total = trx.cardTransaction.total,
                    tanggal = trx.cardTransaction.tanggal,
                    tint = trx.cardTransaction.tint,
                    modifier = modifier.clickable{ navigateToDetail(trx.cardTransaction.id) }
                )
            }
//        }
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