package com.example.temansawit.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.temansawit.database.TemansawitDatabase
import com.example.temansawit.model.Trx
import com.example.temansawit.model.TrxDataSource
import com.example.temansawit.network.ApiService
import com.example.temansawit.network.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class Repository(private val temansawitDatabase: TemansawitDatabase, private val apiService: ApiService) {
    private val income = mutableListOf<IncomeResponseItem>()
    private val loginResult = mutableListOf<AuthResponse>()
    private val transaction2 = mutableListOf<Trx>()

    init {
        if (transaction2.isEmpty()) {
            TrxDataSource.dummyTransaction.forEach {
                transaction2.add(Trx(it))
            }
        }
    }

    fun loginUser(username: String, password: String): LiveData<Result<AuthResponse>> = liveData {
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
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
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        emit(Result.Loading)
        try {
            val response = apiService.register(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "registerUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    //masih error
    fun getNewToken(): LiveData<Result<NewTokenResponse>> = liveData {
        try {
            val response = apiService.getNewToken()
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getNewToken: ${e.message.toString()}")
        }
    }

    fun getUserProfile(): LiveData<Result<UserResponse>> = liveData {
        try {
            val responseData = mutableStateOf("")
            val response = apiService.userProfile()
            responseData.value = response.fullName.toString()
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "getUserProfile: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun createIncome(
        userId: Int, trx_time: String, price: Int, total: Int, description: String? = null
    ): LiveData<Result<IncomeResponse>> = liveData {
        val json = JSONObject()
        json.put("userId", userId)
        json.put("transaction_time", trx_time)
        json.put("price", price)
        json.put("total_weight", total)
        json.put("description", description)
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        emit(Result.Loading)
        try {
            val response = apiService.createIncome(requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, "createIncome: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

//    fun getIncome(): LiveData<Result<List<IncomeResponseItem>>> = liveData {
//        try {
//            val response = apiService.getIncome()
//            emit(Result.Success(response))
//        } catch (e: Exception) {
//            Log.e(TAG, "getIncome: ${e.message.toString()}")
//            emit(Result.Error(e.message.toString()))
//        }
//    }
    fun getIncome(): Flow<List<IncomeResponseItem>> = flow {
        val response = apiService.getIncome()
        emit(response)
    }
    fun getIncomeById(incomeId: Int): Flow<IncomeResponseItem> = flow {
        val response = apiService.getIncomeById(incomeId)
        emit(response)
    }

//    fun getAllTrx() : Flow<MutableList<IncomeResponseItem>> = flowOf(transaction)

    companion object {
        private const val TAG = "Repository"
    }
//
//    fun getAllTrx(): Flow<List<Trx>> = flowOf(transaction)
//
    fun getTrxById(transactionId: Int): Trx {
        return transaction2.first {
            it.cardTransaction.id == transactionId
        }
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