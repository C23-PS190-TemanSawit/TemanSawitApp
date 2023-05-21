package com.example.temansawit.model

import androidx.compose.ui.graphics.Color
import com.example.temansawit.ui.theme.GreenPrimary

data class CardTransaction(
    val deskripsi: String,
    val total: String,
    val tanggal: String,
    val tint: Color
)

val dummyTransaction = listOf(
    CardTransaction("120", "Rp 2.000.000", "Selasa, 21 Juli 2023", GreenPrimary),
    CardTransaction("5000", "Rp 5.065.000.000", "Selasa, 21 Juli 2023", GreenPrimary)
)