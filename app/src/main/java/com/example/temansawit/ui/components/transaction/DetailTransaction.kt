package com.example.temansawit.ui.components.transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temansawit.R
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.network.response.OutcomeResponseItem
import com.example.temansawit.ui.theme.GreenPrimary
import com.example.temansawit.ui.theme.TemanSawitTheme

@Composable
fun CardNoTransaksi(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color,
    jenis_transaksi: String
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier
                .padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painter,
                    contentDescription = "pemasukan",
                    tint = tint
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = jenis_transaksi,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CardIncomeDetail(
    detailTrx: IncomeResponseItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier
                .padding(start = 16.dp, end = 16.dp, top = 21.dp, bottom = 21.dp),
        ) {
            Column {
                Card(
                    modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, SolidColor(Color.Black))),
                ) {
                    Row(
                        modifier
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = "Tanggal transaksi"
                        )
                        Spacer(modifier.padding(8.dp))
                        Text(
                            text = detailTrx.transactionTime,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Row(
                ) {
                    Text(
                        text = "Harga per Kg",
                        modifier.weight(2f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF727970),
                        )
                    Text(
                        text = detailTrx.price.toString(),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                ) {
                    Text(
                        text = "Total Berat",
                        modifier.weight(2f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF727970),

                        )
                    Text(
                        text = "${detailTrx.totalWeight} Kilogram",
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Pemasukan",
                        modifier.weight(2f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        )
                    Text(
                        text = (detailTrx.price * detailTrx.totalWeight).toString(),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Row(
                    modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GreenPrimary.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Deskripsi",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                        Text(
                            text = detailTrx.description,
                            modifier.padding(top = 8.dp),
                            fontSize = 14.sp,
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CardOutcomeDetail(
    detailTrx: OutcomeResponseItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier
                .padding(start = 16.dp, end = 16.dp, top = 21.dp, bottom = 21.dp),
        ) {
            Column {
                Card(
                    modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, SolidColor(Color.Black))),
                ) {
                    Row(
                        modifier
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                            contentDescription = "Tanggal transaksi"
                        )
                        Spacer(modifier.padding(8.dp))
                        Text(
                            text = detailTrx.transactionTime,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Row(
                ) {
                    Text(
                        text = "Total Pengeluaran",
                        modifier.weight(2f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF727970),
                        )
                    Text(
                        text = detailTrx.total_outcome.toString(),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                Row(
                    modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GreenPrimary.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Deskripsi",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                        Text(
                            text = detailTrx.description,
                            modifier.padding(top = 8.dp),
                            fontSize = 14.sp,
                        )
                    }
                }

            }
        }
    }
}
