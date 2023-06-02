package com.example.temansawit.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPressed

@Composable
fun AboutUs(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Tentang Kami") },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_navigate_before_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFFC0DBCE))
                .verticalScroll(rememberScrollState())
        ) {
            Component4()
        }
    }
}



@Composable
fun Component4() {
    Ukuran(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
        ) {
            AboutR()
        }
        AboutCart()

    }
}

@Composable
fun Ukuran(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        val height = maxOf(largePlaceable.height, smallPlaceable.height)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            largePlaceable.placeRelative(0, 0)
            smallPlaceable.placeRelative(
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = height - smallPlaceable.height / 2
            )
        }
    }
}

@Composable
fun AboutR() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.brand),
            contentDescription = "Your Image",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )

        Text(text = "TemanSawit merupakan aplikasi blablabbla. Aplikasi ini dikembangkan oleh tim capstone Bangkit Academy 2023, dengan anggota tim:",
            textAlign = TextAlign.Justify,  modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun AboutCart() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = "Noor Saputri",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Mobile Development",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column {
                        Text(
                            text = "M. Sohiburroihan Akbar",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Mobile Development",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column {
                        Text(
                            text = "Syafitri Rihandini",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Machine Learning",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column {
                        Text(
                            text = "Aditya Shiva Al-Hakim",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Machine Learning",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column {
                        Text(
                            text = "Rischa Nurul Hidayati",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Cloud Computing",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column {
                        Text(
                            text = "Muh. Falach Achsan Yusuf",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            text = "Cloud Computing",
                            style = TextStyle(fontSize = 14.sp,                         color = Color.Gray
                            )
                        )
                    }
                }

            }


        }
    }

}