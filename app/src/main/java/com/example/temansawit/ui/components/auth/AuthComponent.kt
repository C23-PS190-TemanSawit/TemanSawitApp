package com.example.temansawit.ui.components.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.ui.theme.Raleway
import com.example.temansawit.ui.theme.Roboto

@Composable
fun LogoTemanSawit(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 26.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .size(41.dp)
//            .shadow(1.dp, clip = true)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_temansawit2),
                contentDescription = "logo teman sawit",
                modifier
                    .padding(4.dp)
                    .size(width = 32.dp, height = 32.dp)
            )
        }
        Spacer(modifier = modifier.padding(horizontal = 4.dp))
        Text(
            text = "Temansawit",
            color = GreenPrimary,
            fontFamily = Raleway,
//            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun AuthText(
    modifier: Modifier = Modifier,
    loginText: String,
    loginBodyText: String
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Text(
            text = loginText,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Roboto
        )
        Text(
            text = loginBodyText,
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}