package com.example.temansawit.data

import com.example.temansawit.model.Trx
import com.example.temansawit.model.TrxDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {
    private val transaction = mutableListOf<Trx>()

    init {
        if (transaction.isEmpty()) {
            TrxDataSource.dummyTransaction.forEach {
                transaction.add(Trx(it))
            }
        }
    }

    fun getAllTrx(): Flow<List<Trx>> = flowOf(transaction)

    fun getTrxById(transactionId: Long): Trx {
        return transaction.first {
            it.cardTransaction.id == transactionId
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}