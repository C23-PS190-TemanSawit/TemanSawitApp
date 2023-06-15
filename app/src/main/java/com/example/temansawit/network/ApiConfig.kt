package com.example.temansawit.network

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private fun getInterceptor(token: String?, context: Context): OkHttpClient {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token, context))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        fun getApiService(context: Context): ApiService {
            val sharedPreferences =
                context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("access_token", null).toString()
            Log.d("ApiConfig", token)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://temansawit-api-sqmlxtcfma-ts.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token, context))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}