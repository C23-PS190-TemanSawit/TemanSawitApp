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

fun PrivacyPolicy( modifier: Modifier = Modifier , navigateBack: () -> Unit){
    Scaffold(

        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Kebijakan Privasi") },
                backgroundColor =MaterialTheme.colors.primaryVariant,
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
                    text = "Kami menjaga privasi pengguna dengan serius. Kami hanya mengumpulkan informasi yang diperlukan untuk memberikan layanan kami. Informasi pribadi seperti nama, alamat email, dan nomor telepon hanya digunakan untuk tujuan aplikasi dan tidak akan dibagikan tanpa izin tertulis. Kami melindungi informasi pribadi dari akses tidak sah dan mengikuti tindakan keamanan yang sesuai. Pengguna dapat mengakses, memperbarui, atau menghapus informasi pribadi mereka. Dengan menggunakan aplikasi kami, pengguna menyetujui kebijakan privasi ini. Perubahan pada kebijakan privasi akan diinformasikan melalui pembaruan aplikasi atau kontak yang terdaftar.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(vertical = 8.dp)
                )


            }})
}