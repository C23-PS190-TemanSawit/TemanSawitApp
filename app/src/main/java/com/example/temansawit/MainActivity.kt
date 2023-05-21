package com.example.temansawit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.components.home.CardTransaction
import com.example.temansawit.components.home.Pendapatan
import com.example.temansawit.components.home.Welcome
import com.example.temansawit.components.navigation.BottomBar
import com.example.temansawit.model.CardTransaction
import com.example.temansawit.model.dummyTransaction
import com.example.temansawit.ui.theme.Green700
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.TemanSawitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanSawitTheme {
                // A surface container using the 'background' color from the theme
                TemanSawitApp()
            }
        }
    }
}

@Composable
fun TemanSawitApp() {
    Scaffold(
        bottomBar = { BottomBar() },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        scaffoldState = rememberScaffoldState(),
        floatingActionButton = {
                               FloatingActionButton(
                                   shape = CircleShape,
                                   onClick = {  },
                                   backgroundColor = Green700,
                               ) {
                                   Icon(
                                       painter = painterResource(id = R.drawable.outline_camera_alt_24),
                                       contentDescription = "Deteksi Sawit")
                               }
        },
        content = { it
            Row(
                modifier = Modifier.fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
                ) {
                    Component1()
                    Spacer(modifier = Modifier.padding(top = 100.dp))
                    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        Transaction(dummyTransaction)
                    }
                }
            }
        }
    )
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
            Welcome(name = "Jasmine")
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

@Composable
fun Transaction(
    listTransaction: List<CardTransaction>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTransaction, key = { it.deskripsi }) { trx ->
            CardTransaction(transaction = trx)
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TemanSawitTheme {
        TemanSawitApp()
    }
}