package com.example.temansawit.components.home

import android.os.Build
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.model.CardTransaction
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.ui.theme.TemanSawitTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CardTransaction(
    transaction: CardTransaction
) {
    Card(
        modifier = Modifier
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
                    text = transaction.deskripsi,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
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
                    text = transaction.total,
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
                        text = transaction.tanggal,
                        color = Color(0xFF68707C),
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp)
                }
            }
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                contentDescription = "Notifikasi",
                tint = transaction.tint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TemanSawitTheme {
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)
        CardTransaction(
            transaction = CardTransaction("120\nKilogram", "Rp 2.000.000", formatted, GreenPrimary)
        )
    }
}