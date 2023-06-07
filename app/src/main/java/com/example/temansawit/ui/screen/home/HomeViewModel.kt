package com.example.temansawit.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.IncomeResponseItem
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<IncomeResponseItem>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<IncomeResponseItem>>>
        get() = _uiState

    private val _userIdState = MutableLiveData<Int>()
    val userIdState: LiveData<Int> get() =  _userIdState
    private val _tanggalTrx = MutableLiveData("")
    val tanggalTrx: LiveData<String> get() =  _tanggalTrx
    private val _harga = MutableLiveData<Int>()
    val harga: LiveData<Int> get() =  _harga
    private val _berat = MutableLiveData<Int>()
    val berat: LiveData<Int> get() =  _berat
    private val _deskripsi = MutableLiveData("")
    val deskripsi: LiveData<String> get() =  _deskripsi

    fun onUserIdChange(userId: String) {
        val intValue = userId.toIntOrNull() ?: 0
        _userIdState.value = intValue
    }
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
    fun onDescChange(desc: String) {
        _deskripsi.value = desc
    }


    fun getUserProfile() = repository.getUserProfile()
    fun getNewToken() = repository.getNewToken()

    fun createIncome(userId: Int, trx_time: String, price: Int, total: Int, description: String? = null) =
        repository.createIncome(userId, trx_time, price, total, description)

    val _income = MutableLiveData<List<IncomeResponseItem>>()
    val income: LiveData<List<IncomeResponseItem>> get() = _income
    fun getIncome() {
        viewModelScope.launch {
            repository.getIncome()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { income ->
                    _uiState.value = UiState.Success(income)
                }
        }
    }

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