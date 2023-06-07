package com.example.temansawit.ui.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.temansawit.data.Repository

class LoginViewModel(private val repository: Repository): ViewModel() {
    private val _usernameState = MutableLiveData("")
    val usernameState: LiveData<String> get() =  _usernameState
    private val _passwordState = MutableLiveData("")
    val passwordState: LiveData<String> get() =  _passwordState


    fun onUsernameChange(username: String) {
        _usernameState.value = username
    }
    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }

    fun loginUser(username: String, password: String) = repository.loginUser(username, password)

}