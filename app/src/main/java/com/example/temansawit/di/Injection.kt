package com.example.temansawit.di


import android.content.Context
import com.example.temansawit.data.Repository
import com.example.temansawit.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository(apiService)
    }
}