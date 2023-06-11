package com.example.temansawit.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.temansawit.network.ApiService
import com.example.temansawit.network.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class Repository(private val apiService: ApiService) {

    fun loginUser(username: String, password: String): LiveData<Result<AuthResponse>> = liveData {
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        emit(Result.Loading)
        try {
            val response = apiService.login(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "LoginUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(
        username: String, email: String, password: String, confirmPassword: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        val json = JSONObject()
        json.put("username", username)
        json.put("email", email)
        json.put("password", password)
        json.put("confPassword", confirmPassword)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        emit(Result.Loading)
        try {
            val response = apiService.register(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "registerUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun logoutUser(): LiveData<Result<RegisterResponse>> = liveData {
        try {
            val respone = apiService.logout()
            Log.d(TAG, respone.toString())
            emit(Result.Success(respone))
        } catch (e: Exception) {
            Log.e(TAG, "logoutRepository: ${e.message.toString()}")
        }
    }

    //masih error
//    fun getNewToken(): LiveData<Result<NewTokenResponse>> = liveData {
//        try {
//            val response = apiService.getNewToken()
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            Log.e(TAG, "getNewToken: ${e.message.toString()}")
//        }
//    }

    fun getUserProfile(): Flow<UserResponse> = flow {
        try {
            val responseData = mutableStateOf("")
            val response = apiService.userProfile()
            responseData.value = response.fullName.toString()
            emit(response)
        } catch (e: Exception) {
            Log.e(TAG, "getUserProfile: ${e.message.toString()}")
        }
    }

    fun createIncome(
        trx_time: String, price: Int, total: Int, description: String? = null
    ): LiveData<Result<TrxResponse>> = liveData {
        val json = JSONObject()
        json.put("transaction_time", trx_time)
        json.put("price", price)
        json.put("total_weight", total)
        json.put("description", description)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        emit(Result.Loading)
        try {
            val response = apiService.createIncome(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "createIncome: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getIncome(): Flow<List<IncomeResponseItem>> = flow {
        val response = apiService.getIncome()
        emit(response)
    }
    fun getOutcome(): Flow<List<OutcomeResponseItem>> = flow {
        val response = apiService.getOutcome()
        emit(response)
    }
    fun getIncomeById(incomeId: Int): Flow<IncomeResponseItem> = flow {
        val response = apiService.getIncomeById(incomeId).first()
        emit(response)
    }
    fun getOutcomeById(outcomeId: Int): Flow<OutcomeResponseItem> = flow {
        val response = apiService.getOutcomeById(outcomeId).first()
        emit(response)
    }

    fun createOutcome(
        trx_time: String, total_outcome: Int, description: String? = null
    ): LiveData<Result<TrxResponse>> = liveData {
        val json = JSONObject()
        json.put("transaction_time", trx_time)
        json.put("total_outcome", total_outcome)
        json.put("description", description)
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        emit(Result.Loading)
        try {
            val response = apiService.createOutcome(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "createOutcome: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }


//    fun getAllTrx() : Flow<MutableList<IncomeResponseItem>> = flowOf(transaction)

    companion object {
        private const val TAG = "Repository"
    }
//    companion object {
//        @Volatile
//        private var instance: Repository? = null
//
//        fun getInstance(): Repository =
//            instance ?: synchronized(this) {
//                Repository().apply {
//                    instance = this
//                }
//            }
//    }
}