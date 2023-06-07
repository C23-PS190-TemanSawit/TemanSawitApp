package com.example.temansawit.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TransactiomViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<IncomeResponseItem>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<IncomeResponseItem>>
        get() = _uiState

    fun getIncomeById(incomeId: Int) {
        viewModelScope.launch {
            repository.getIncomeById(incomeId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { income ->
                    _uiState.value = UiState.Success(income)
                }
        }
    }
}