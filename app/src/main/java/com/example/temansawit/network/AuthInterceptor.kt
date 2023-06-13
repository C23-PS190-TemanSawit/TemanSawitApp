package com.example.temansawit.network

import android.content.Context
import android.util.Log
import com.example.temansawit.di.Preferences
import com.example.temansawit.network.response.NewTokenResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private var token: String, private var context: Context): Interceptor {
    companion object {
        const val AUTHORIZATION = "Authorization"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        request = if (request.header("No-Authentication") == null && token.isNotEmpty()) {
            val accessToken = token

            request.newBuilder()
                .removeHeader(AUTHORIZATION)
                .addHeader(AUTHORIZATION, accessToken)
                .build()
        } else {
            request.newBuilder()
                .build()
        }
        val response = chain.proceed(request)
            if (response.code == 403) {
                // TODO : save new access token to shared preference
                val sharedPreferences =
                    context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access_token", null).toString()

                request =
                    request.newBuilder()
                        .removeHeader(AUTHORIZATION)
                        .addHeader(AUTHORIZATION, token)
                        .build()
                return chain.proceed(request)
            }
        return response
    }
}