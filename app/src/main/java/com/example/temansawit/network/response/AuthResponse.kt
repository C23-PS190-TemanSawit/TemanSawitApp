package com.example.temansawit.network.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String,
)

data class RegisterResponse(
	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("message")
	val message: String
)