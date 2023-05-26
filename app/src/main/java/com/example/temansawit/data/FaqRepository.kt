package com.example.temansawit.data

import com.example.temansawit.model.FaqModel
import com.example.temansawit.model.Faqs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FaqRepository {
    private val FaqList = mutableListOf<FaqModel>()

    init {
        if (FaqList.isEmpty()) {
            Faqs.faqs.forEach {
                FaqList.add(FaqModel(it, 0))
            }
        }
    }

    fun getAllFaqs() : Flow<List<FaqModel>> {
        return flowOf(FaqList)
    }

    companion object{
        @Volatile
        private var istance : FaqRepository ?= null
        fun getInstance() : FaqRepository =
            istance ?: synchronized(this){
                FaqRepository().apply { istance =this }
            }
    }
}