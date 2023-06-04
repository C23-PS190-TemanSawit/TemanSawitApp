package com.example.temansawit.network.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("acccessToken")
	val acccessToken: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
