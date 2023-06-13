package com.example.temansawit.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.example.temansawit.data.Repository
import com.example.temansawit.network.response.RegisterResponse
import com.example.temansawit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    private val _logout: MutableStateFlow<UiState<RegisterResponse>> = MutableStateFlow(
        UiState.Loading)
    val logout: StateFlow<UiState<RegisterResponse>>
        get() = _logout

    fun logoutUser() = repository.logoutUser()
    fun changePassword(
        password: String, newPassword: String, confPassword: String
    ) = repository.changePassword(password, newPassword, confPassword)

    fun updateProfile(
        fullname: String, phoneNumber: String, birthDate: String, gender: String
    ) = repository.updateProfile(fullname, phoneNumber, birthDate, gender)
}