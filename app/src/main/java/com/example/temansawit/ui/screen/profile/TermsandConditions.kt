package com.example.temansawit.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPressed

@Composable
fun TermsandConditions( modifier: Modifier = Modifier , navigateBack: () -> Unit){
    Scaffold(

        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Syarat & Ketentuan") },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White,
                elevation = 10.dp,
                navigationIcon = {
                    IconButton(onClick =  navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_navigate_before_24),
                            contentDescription = "Back"
                        )
                    }
                }

            )
        },
        content = { it
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(R.drawable.brand),
                    contentDescription = "Your Image",
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Update : 3 Juni 2023",modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text=" Dengan menggunakan aplikasi kami, Anda setuju untuk:\n" +
                            "\n" +
                            "1. Menggunakan aplikasi ini hanya untuk tujuan yang sah dan tidak melanggar hukum.\n" +
                            "2. Tidak menggandakan, mendistribusikan, atau menggunakan konten aplikasi tanpa izin tertulis.\n" +
                            "3. Memahami bahwa ketersediaan aplikasi tidak dijamin dan kami tidak bertanggung jawab atas kerusakan yang timbul dari penggunaannya.\n" +
                            "4. Mengetahui bahwa tautan eksternal dalam aplikasi bukan tanggung jawab kami dan penggunaannya menjadi tanggung jawab Anda.\n" +
                            "5. Kami akan melindungi privasi Anda sesuai dengan kebijakan privasi yang berlaku.\n" +
                            "6. Memahami bahwa syarat dan ketentuan ini dapat berubah, dan perubahan akan diinformasikan kepada Anda.\n" +
                            "\n" +
                            "Dengan menggunakan aplikasi kami, Anda menyetujui syarat dan ketentuan ini. Jika Anda tidak setuju, harap hentikan penggunaan aplikasi kami.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(vertical = 8.dp)
                )


            }})
}