package com.example.temansawit.di


import android.content.Context
import com.example.temansawit.data.Repository
import com.example.temansawit.database.TemansawitDatabase
import com.example.temansawit.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = TemansawitDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return Repository(database, apiService)
    }
}