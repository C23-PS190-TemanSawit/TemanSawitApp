package com.example.temansawit.network

import com.example.temansawit.network.response.*
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {
    @POST("/api/login")
    suspend fun login(
        @Body loginInfo: RequestBody
    ): AuthResponse

    @POST("/api/users")
    suspend fun register(
        @Body registerInfo: RequestBody
    ): RegisterResponse

    @GET("/api/token")
    suspend fun getNewToken(): NewTokenResponse

    @GET("/api/profile")
    suspend fun userProfile(): UserResponse

    @POST("/api/income")
    suspend fun createIncome(
        @Body incomeInput: RequestBody
    ): IncomeResponse
    @GET("/api/income")
    suspend fun getIncome(): List<IncomeResponseItem>
    @GET("/api/income/{id}")
    suspend fun getIncomeById(
        @Path("incomeId") incomeId: Int
    ): IncomeResponseItem

}