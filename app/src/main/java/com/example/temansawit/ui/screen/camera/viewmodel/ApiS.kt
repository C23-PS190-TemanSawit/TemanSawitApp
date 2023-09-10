package com.example.temansawit.ui.screen.camera.viewmodel

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


data class ModelResponse(
    @field:SerializedName("prob")
    val prob: List<Any?>? = null,

    @field:SerializedName("classes")
    val classes: List<String?>? = null,

    @field:SerializedName("top_2")
    val top2: Top2
)

data class Top2(
    @field:SerializedName("ripe")
    val ripe: Float,
    @field:SerializedName("underripe")
    val underripe: Float,
    @field:SerializedName("unripe")
    val unripe: Float,
    @field:SerializedName("overripe")
    val overripe: Float,
    @field:SerializedName("rotten")
    val rotten: Float,
    @field:SerializedName("empty_bunch")
    val empty_bunch: Float,

)


interface ApiService {
    @Multipart
    @POST("/api/predict")
    fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Call<ModelResponse>
}

class ApiS{
    fun getApiService2(): ApiService{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
//            .baseUrl("https://temansawit-ml-api-sqmlxtcfma-ts.a.run.app")
            .baseUrl("https://temansawit-ml-api-khtk6p34bq-ts.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}