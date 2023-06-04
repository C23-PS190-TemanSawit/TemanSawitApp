package com.example.temansawit.network

import com.example.temansawit.network.response.AuthResponse
import com.example.temansawit.network.response.IncomeResponse
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") email:String,
        @Field("password") password: String
    ): AuthResponse


    @FormUrlEncoded
    @POST("createIncome")
    suspend fun createIncome(
    ): List<IncomeResponse>
}