package com.example.temansawit.network

import com.example.temansawit.network.response.*
import okhttp3.MultipartBody
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

    @PUT("/api/update")
    suspend fun changePassword(
        @Body changePassword: RequestBody
    ): RegisterResponse

    @PUT("/api/forgotPassword")
    suspend fun forgotPassword(
        @Body forgotPassword: RequestBody
    ): RegisterResponse

    @Multipart
    @POST("/api/upload")
    suspend fun changePhoto(
        @Part file: MultipartBody.Part,
    ): RegisterResponse

    @PUT("/api/profile")
    suspend fun updateProfile(
        @Body updateProfile: RequestBody
    ): RegisterResponse

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

    @GET("/api/outcome/{id}")
    suspend fun getOutcomeById(
        @Path("id") outcomeId: Int
    ): List<OutcomeResponseItem>

    @POST("/api/outcome")
    suspend fun createOutcome(
        @Body outcomeInput: RequestBody
    ): TrxResponse

    @GET("/api/outcome")
    suspend fun getOutcome(): List<OutcomeResponseItem>
}