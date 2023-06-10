package com.example.temansawit.ui.screen.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.network.response.OutcomeResponseItem
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

    private val _income: MutableStateFlow<UiState<List<IncomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val income: StateFlow<UiState<List<IncomeResponseItem>>>
        get() = _income

    private val _outcome: MutableStateFlow<UiState<List<OutcomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val outcome: StateFlow<UiState<List<OutcomeResponseItem>>>
        get() = _outcome

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
    fun getOutcome() {
        viewModelScope.launch {
            repository.getOutcome()
                .catch {
                    _outcome.value = UiState.Error(it.message.toString())
                }
                .collect { outcome ->
                    _outcome.value = UiState.Success(outcome)
                }
        }
    }

    fun getIncomeById(incomeId: Int) {
        viewModelScope.launch {
            repository.getIncomeById(incomeId)
                .catch {
                    Log.d("tes", it.message.toString())
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { income ->
                    _uiState.value = UiState.Success(income)
                }
        }
    }
}