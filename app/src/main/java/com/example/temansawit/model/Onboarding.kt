package com.example.temansawit.model

import androidx.annotation.DrawableRes
import com.example.temansawit.R

data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

val pages = listOf(
    OnboardingPage(R.drawable.onboarding1, "Bibit", "Optimalkan produksi kelapa sawit Anda dengan teknologi terbaru dalam deteksi abnormalitas pada tanaman."),
    OnboardingPage(R.drawable.onboarding2, "Buah", "Ciptakan keuntungan lebih dengan deteksi kematangan buah kelapa sawit yang akurat dan efisien."),
    OnboardingPage(R.drawable.onboarding2, "Keuntungan", "Catat setiap pemasukan dan pengeluaran penjualan kelapa sawit Anda dengan mudah dan akurat."),
)