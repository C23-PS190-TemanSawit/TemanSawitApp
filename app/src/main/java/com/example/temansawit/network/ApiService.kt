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

    @DELETE("/api/logout")
    suspend fun logout(): RegisterResponse

//    @GET("/api/token")
//    suspend fun getNewToken(): NewTokenResponse

    @GET("/api/profile")
    suspend fun userProfile(): UserResponse

    @POST("/api/income")
    suspend fun createIncome(
        @Body incomeInput: RequestBody
    ): TrxResponse
    @GET("/api/income")
    suspend fun getIncome(): List<IncomeResponseItem>
    @GET("/api/income/{id}")
    suspend fun getIncomeById(
        @Path("id") incomeId: Int
    ): List<IncomeResponseItem>

    @POST("/api/outcome")
    suspend fun createOutcome(
        @Body outcomeInput: RequestBody
    ): TrxResponse

    @GET("/api/outcome")
    suspend fun getOutcome(): List<OutcomeResponseItem>
}