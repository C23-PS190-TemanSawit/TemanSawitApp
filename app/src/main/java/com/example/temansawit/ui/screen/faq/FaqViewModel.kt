package com.example.temansawit.ui.screen.faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.FaqRepository
import com.example.temansawit.model.FaqModel
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FaqViewModel (
    private val repository : FaqRepository
        ) : ViewModel() {
            private val _uiState : MutableStateFlow<UiState<List<FaqModel>>> = MutableStateFlow(UiState.Loading)
        val uiState : StateFlow<UiState<List<FaqModel>>>
        get() = _uiState

        fun getAllFaqs(){
            viewModelScope.launch {
                repository.getAllFaqs()
                    .catch {
                        _uiState.value =UiState.Error(it.message.toString())
                    }
                    .collect{FaqModel ->
                        _uiState.value = UiState.Success(FaqModel)
                    }
            }
        }

        }

