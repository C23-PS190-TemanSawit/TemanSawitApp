package com.example.temansawit.ui.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.ui.theme.GreenPrimary

@Composable
fun IncomeCard(
    berat: Int,
    total: Int,
    tanggal: String,
    modifier: Modifier = Modifier
) {
    val totalWithFormat = String.format("%,d", total).replace(",", ".")
    Card(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(18.dp))
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(18.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    modifier = modifier.width(57.dp),
                    text = berat.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Kilogram",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )

            }
            Spacer(modifier = Modifier.padding(18.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Rp $totalWithFormat",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(10.dp),
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = "My Icon",
                        tint = Color(0xFF4022F4),
                    )
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Text(
                        text = tanggal,
                        color = Color(0xFF68707C),
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp)
                }
            }
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                contentDescription = "Notifikasi",
                tint = Color.Green
            )
        }
    }
}

@Composable
fun OutcomeCard(
    total_outcome: Int,
    tanggal: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val totalWithFormat = String.format("%,d", total_outcome).replace(",", ".")
    Card(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(18.dp))
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(18.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    modifier = modifier.width(57.dp),
                    text = description,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }
            Spacer(modifier = Modifier.padding(18.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Rp $totalWithFormat",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(10.dp),
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = "My Icon",
                        tint = Color(0xFF4022F4),
                    )
                    Spacer(modifier = Modifier.padding(start = 4.dp))
                    Text(
                        text = tanggal,
                        color = Color(0xFF68707C),
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp)
                }
            }
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_circle_up_24),
                contentDescription = "Pengeluaran",
                tint = Color.Red
            )
        }
    }
}