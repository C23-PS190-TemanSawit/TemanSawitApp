package com.example.temansawit.network

import android.content.Context
import com.example.temansawit.di.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private fun getInterceptor(token: String?): OkHttpClient {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        fun getApiService(context: Context): ApiService {
            val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val preferences = Preferences.initPref(context, "isLoggedIn")
            val token = Preferences.getToken(sharedPreferences)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://temansawit-api-sqmlxtcfma-ts.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}