package com.example.temansawit.util

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.CombinedResponse
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: Repository) : ViewModel() {
    private val _combinedResponse = MutableStateFlow<UiState<CombinedResponse>>(UiState.Loading)
    val combinedResponse: StateFlow<UiState<CombinedResponse>> = _combinedResponse

    fun fetchCombinedResponse() {
        viewModelScope.launch {
            try {
                val response1Flow = repository.getIncome()
                val response2Flow = repository.getOutcome()

                response1Flow.zip(response2Flow) { response1, response2 ->
                    CombinedResponse(response1, response2)
                }.collect { combinedResponse ->
                    _combinedResponse.value = UiState.Success(combinedResponse)
                    Log.d("ahayy", combinedResponse.toString())
                }
            } catch (e: Exception) {
                _combinedResponse.value = UiState.Error(e.message.toString())
            }
        }
    }
}