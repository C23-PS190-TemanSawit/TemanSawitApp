package com.example.temansawit.network.response

import com.google.gson.annotations.SerializedName

data class TrxResponse(
	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("IncomeResponse")
	val incomeResponse: List<IncomeResponseItem>,

	@field:SerializedName("OutcomeResponse")
	val outcomeResponse: List<OutcomeResponseItem>
)

data class IncomeResponseItem(
	@field:SerializedName("incomeId")
	val incomeId: Int,

	@field:SerializedName("transaction_time")
	val transactionTime: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("total_weight")
	val totalWeight: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("userId")
	val userId: Int,
)

data class OutcomeResponseItem(
	@field:SerializedName("outcomeId")
	val outcomeId: Int,

	@field:SerializedName("transaction_time")
	val transactionTime: String,

	@field:SerializedName("total_outcome")
	val total_outcome: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("userId")
	val userId: Int,
)

data class CombinedResponse(
	val incomeItems: List<IncomeResponseItem>,
	val outcomeItems: List<OutcomeResponseItem>
)