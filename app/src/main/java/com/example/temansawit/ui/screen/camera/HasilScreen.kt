package com.example.temansawit.ui.screen.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R


@Composable
fun HasilScreen(modifier: Modifier = Modifier) {
    val income1 = remember { mutableStateOf("") }
    val income2 = remember { mutableStateOf("") }
    val income3 = remember { mutableStateOf("") }
    val income4 = remember { mutableStateOf("") }
    val genderOptions = listOf("Laki-laki", "Wanita")
    val selectedGender = remember { mutableStateOf("")
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Hasil Deteksi") },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation back */ }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_navigate_before_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {it
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        painter = painterResource(id = R.drawable.sawit),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,

                        )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        painter = painterResource(id = R.drawable.sukses),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,

                        )

                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Deteksi Sukses")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Hasil deteksi kematangan buah kelapa sawit Anda")
                Spacer(modifier = Modifier.height(30.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    backgroundColor = Color.Green,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Keluar",
                        )
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    backgroundColor = Color.Red,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Keluar",
                        )
                    }

                }
                Spacer(modifier = modifier.padding(8.dp))
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(100.dp)),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "LANJUTKAN DETEKSI")
                }
            }
        }
    )
}
