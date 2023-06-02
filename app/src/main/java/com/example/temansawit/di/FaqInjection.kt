package com.example.temansawit.di

import com.example.temansawit.data.FaqRepository

object FaqInjection {
    fun provideRepository(): FaqRepository {
        return FaqRepository.getInstance()
    }
}