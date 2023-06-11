package com.example.temansawit.network

import android.content.Context
import android.util.Log
import com.example.temansawit.di.Preferences
import com.example.temansawit.network.response.NewTokenResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private var token: String): Interceptor {
    companion object {
        const val AUTHORIZATION = "Authorization"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        request = if (request.header("No-Authentication") == null && token.isNotEmpty()) {
            val token = "$token"

            request.newBuilder()
                .addHeader(AUTHORIZATION, token)
                .build()
        } else {
            request.newBuilder()
                .build()
        }
        return chain.proceed(request)
//        try {
//            if (response.code == 403) {
//                // TODO : save new access token to shared preference
//                val sharedPreferences =
//                    context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
//                val getRefreshToken = Preferences.getRefreshToken(sharedPreferences)
//                val newRequest = Request
//                    .Builder()
//                    .url("https://temansawit-api-sqmlxtcfma-ts.a.run.app/api/token")
//                    .removeHeader(AUTHORIZATION)
//                    .addHeader(AUTHORIZATION, getRefreshToken).build()
//                val refreshTokenResponse = chain.proceed(newRequest)
//                val responseBody = refreshTokenResponse.body
//                val jsonString = responseBody?.string() ?: ""
//                Log.d("tes", jsonString)
//                val responseTokenObject = Gson().fromJson(jsonString, NewTokenResponse::class.java)
//                Preferences.saveAccessToken(responseTokenObject.accessToken, sharedPreferences)
//
//                request =
//                    request.newBuilder().addHeader(AUTHORIZATION, responseTokenObject.accessToken)
//                        .build()
//                return chain.proceed(request)
//            }
//        }catch (_: Throwable) {}
//        return response
    }
}