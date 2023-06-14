package com.example.temansawit.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temansawit.data.Repository
import com.example.temansawit.data.Result
import com.example.temansawit.network.response.RegisterResponse
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    private val _logout: MutableStateFlow<UiState<RegisterResponse>> = MutableStateFlow(
        UiState.Loading
    )
    val logout: StateFlow<UiState<RegisterResponse>>
        get() = _logout

    private val _photo: MutableStateFlow<Result<RegisterResponse>> = MutableStateFlow(
        Result.Loading
    )
    val photo: StateFlow<Result<RegisterResponse>>
        get() = _photo

    fun logoutUser() = repository.logoutUser()
    fun changePassword(
        password: String, newPassword: String, confPassword: String
    ) = repository.changePassword(password, newPassword, confPassword)

    fun updateProfile(
        fullName: String, phoneNumber: String, birthDate: String, gender: String
    ) = repository.updateProfile(fullName, phoneNumber, birthDate, gender)


    fun changePhoto(uri: InputStream) {
        viewModelScope.launch {
            _photo.update {
                repository.changePhoto(uri)
            }
        }
    }
}