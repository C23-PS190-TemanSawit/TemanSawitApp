package com.example.temansawit.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.network.response.UserResponse
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _income: MutableStateFlow<UiState<List<IncomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val income: StateFlow<UiState<List<IncomeResponseItem>>>
        get() = _income
    private val _name: MutableStateFlow<UiState<UserResponse>> = MutableStateFlow(UiState.Loading)
    val name: StateFlow<UiState<UserResponse>>
        get() = _name

    private val _tanggalTrx = MutableLiveData("")
    val tanggalTrx: LiveData<String> get() =  _tanggalTrx
    private val _harga = MutableLiveData<Int>()
    val harga: LiveData<Int> get() =  _harga
    private val _berat = MutableLiveData<Int>()
    val berat: LiveData<Int> get() =  _berat
    private val _totalOutcome = MutableLiveData<Int>()
    val totalOutcome: LiveData<Int> get() =  _totalOutcome
    private val _deskripsi = MutableLiveData("")
    val deskripsi: LiveData<String> get() =  _deskripsi

    fun onTanggalTrxChange(tanggalTrx: String) {
        _tanggalTrx.value = tanggalTrx
    }
    fun onPriceChange(price: String) {
        val intValue = price.toIntOrNull() ?: 0
        _harga.value = intValue
    }
    fun onWeightChange(weight: String) {
        val intValue = weight.toIntOrNull() ?: 0
        _berat.value = intValue
    }
    fun onTotalOutcomeChange(totalOutcome: String) {
        val intValue = totalOutcome.toIntOrNull() ?: 0
        _totalOutcome.value = intValue
    }
    fun onDescChange(desc: String) {
        _deskripsi.value = desc
    }


    fun getUserProfile() {
        viewModelScope.launch {
            repository.getUserProfile()
                .catch {
                    _name.value = UiState.Error(it.message.toString())
                }
                .collect{ name ->
                    _name.value = UiState.Success(name)
                }
        }
    }
    fun createIncome(trx_time: String, price: Int, total: Int, description: String? = null) =
        repository.createIncome(trx_time, price, total, description)

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

    fun createOutcome(trx_time: String, totalOutcome: Int, description: String? = null) =
        repository.createOutcome(trx_time, totalOutcome, description)

//    fun getAllTrx() {
//        viewModelScope.launch {
//            repository.getAllTrx()
//                .catch {
//                    _uiState.value = UiState.Error(it.message.toString())
//                }
//                .collect { trx ->
//                    _uiState.value = UiState.Success(trx)
//                }
//        }
//    }
}