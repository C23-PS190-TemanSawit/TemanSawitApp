package com.example.temansawit.model

import androidx.compose.ui.graphics.Color
import com.example.temansawit.ui.theme.GreenPrimary

data class Trx(
    val cardTransaction: CardTransaction,
)

data class CardTransaction(
    val id: Long,
    val berat: String,
    val hargaPerKg: String,
    val total: String,
    val tanggal: String,
    val deskripsi: String,
    val tint: Color
)

object TrxDataSource {
    val dummyTransaction = listOf(
        CardTransaction(1, "120", "2.500", "Rp 2.000.000", "Selasa, 21 Juli 2023", "yyyy", GreenPrimary),
        CardTransaction(2, "5000", "2.500","Rp 5.065.000.000", "Selasa, 21 Juli 2023","gtw", GreenPrimary),
        CardTransaction(3, "5000", "2.500","Rp 5.065.000.000", "Selasa, 21 Juli 2023", "-", Color.Red)
    )
}