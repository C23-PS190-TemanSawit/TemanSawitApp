package com.example.temansawit.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: Repository) : ViewModel() {
    private val _income: MutableStateFlow<UiState<List<IncomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val income: StateFlow<UiState<List<IncomeResponseItem>>>
        get() = _income

    fun getIncome() {
        viewModelScope.launch {
            repository.getIncome()
                .catch {
                    _income.value = UiState.Error(it.message.toString())
                }
                .collect { income ->
                    _income.value = UiState.Success(income)
                }
        }
    }
}