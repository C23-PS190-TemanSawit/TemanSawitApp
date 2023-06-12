package com.example.temansawit.ui.screen.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.temansawit.data.Repository

class RegisterViewModel(private val repository: Repository): ViewModel() {
    private val _usernameState = MutableLiveData("")
    val usernameState: LiveData<String> get() =  _usernameState
    private val _emailState = MutableLiveData("")
    val emailState: LiveData<String> get() =  _emailState
    private val _passwordState = MutableLiveData("")
    val passwordState: LiveData<String> get() =  _passwordState
    private val _confirmPasswordState = MutableLiveData("")
    val confirmPasswordState: LiveData<String> get() =  _confirmPasswordState


    fun onUsernameChange(username: String) {
        _usernameState.value = username
    }
    fun onEmailChange(email: String) {
        _emailState.value = email
    }
    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }
    fun onConfirmPasswordChange(confirmPassword: String) {
        _confirmPasswordState.value = confirmPassword
    }

    fun registerUser(username: String, email: String, password: String, confirmPassword: String) =
        repository.registerUser(username, email, password, confirmPassword)
}