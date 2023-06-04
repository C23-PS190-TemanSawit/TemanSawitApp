package com.example.temansawit.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.model.Trx
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<IncomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<IncomeResponseItem>>>
        get() = _uiState

    fun getAllTrx() {
        viewModelScope.launch {
            repository.getAllTrx()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { trx ->
                    _uiState.value = UiState.Success(trx)
                }
        }
    }
}