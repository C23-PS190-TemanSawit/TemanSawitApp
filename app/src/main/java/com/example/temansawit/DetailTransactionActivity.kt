package com.example.temansawit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.components.home.Pendapatan
import com.example.temansawit.components.home.Welcome
import com.example.temansawit.components.transaction.CardDetail
import com.example.temansawit.components.transaction.CardNoTransaksi
import com.example.temansawit.model.dummyTransaction
import com.example.temansawit.ui.theme.GreenPressed
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.ui.theme.GreenSurface
import com.example.temansawit.ui.theme.TemanSawitTheme

class DetailTransactionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanSawitTheme {
                // A surface container using the 'background' color from the theme
                DetailTransactionApp()
            }
        }
    }
}


@Composable
fun DetailTransactionApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_before_24),
                            contentDescription = "Kembali",
                            Modifier.size(32.dp)
                        )
                    }
                },
                backgroundColor = GreenPressed,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) { it ->
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
                    CardNoTransaksi()
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    CardDetail()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TemanSawitTheme {
        DetailTransactionApp()
    }
}