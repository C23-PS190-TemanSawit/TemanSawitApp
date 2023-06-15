package com.example.temansawit.ui.screen.faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.temansawit.data.FaqRepository

class VMFaq (private val repository: FaqRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FaqViewModel :: class.java)){
            return FaqViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}