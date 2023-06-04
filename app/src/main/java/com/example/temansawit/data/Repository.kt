package com.example.temansawit.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.temansawit.database.TemansawitDatabase
import com.example.temansawit.model.Trx
import com.example.temansawit.model.TrxDataSource
import com.example.temansawit.network.ApiService
import com.example.temansawit.network.response.AuthResponse
import com.example.temansawit.network.response.IncomeResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository(private val temansawitDatabase: TemansawitDatabase, private val apiService: ApiService) {
    private val transaction = mutableListOf<IncomeResponseItem>()
    private val transaction2 = mutableListOf<Trx>()

    init {
        if (transaction2.isEmpty()) {
            TrxDataSource.dummyTransaction.forEach {
                transaction2.add(Trx(it))
            }
        }
    }

    fun loginUser(username: String, password: String) : LiveData<Result<AuthResponse>> = liveData {

        emit(Result.Loading)
        try {
            val response = apiService.login(username, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("Repository", "loginUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getAllTrx() : Flow<MutableList<IncomeResponseItem>> = flowOf(transaction)

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