package com.example.temansawit.network.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: Any? = null,

	@field:SerializedName("birthDate")
	val birthDate: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

)
