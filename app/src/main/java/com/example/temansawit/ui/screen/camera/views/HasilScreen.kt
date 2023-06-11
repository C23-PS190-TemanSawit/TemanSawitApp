package com.example.temansawit.ui.screen.camera

import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.temansawit.R
import com.example.temansawit.ui.screen.camera.views.DeteksiActivity
import com.example.temansawit.ui.screen.profile.AboutUsActivty
import java.io.File


@Composable
fun HasilScreen(
    file : File?,
    ripe: Float,
    underripe: Float,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
//                        painter = BitmapFactory.decodeFile(file),
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(file)
                                .size(coil.size.Size.ORIGINAL)
                                .build()
                        ),
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
                if (ripe > underripe) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Matang",
                                style = TextStyle(color = Color.Green, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${(ripe * 100)} %",
                                style = TextStyle(color = Color.Green, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )

                        }

                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Belum matang",
                                style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${(underripe * 100)} %",
                                style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )

                        }

                    }
                } else if (ripe < underripe) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Belum matang",
                                style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${(underripe * 100)} %",
                                style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )

                        }

                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Matang",
                                style = TextStyle(color = Color.Green, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${(ripe * 100)} %",
                                style = TextStyle(color = Color.Green, fontWeight = FontWeight.Bold),
                                modifier = Modifier.weight(1f)
                            )

                        }

                    }
                }
                Spacer(modifier = modifier.padding(8.dp))
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(100.dp)),
                    onClick = {  val intent = Intent(context, DeteksiActivity::class.java)
                        context.startActivity(intent) }
                ) {
                    Text(text = "LANJUTKAN DETEKSI")
                }
            }
        }
    )
}
