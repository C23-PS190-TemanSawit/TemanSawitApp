package com.example.temansawit.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.temansawit.di.Injection
import com.example.temansawit.ui.screen.auth.login.LoginViewModel
import com.example.temansawit.ui.screen.auth.register.RegisterViewModel
import com.example.temansawit.ui.screen.home.HomeViewModel
import com.example.temansawit.ui.screen.transaction.TransactiomViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(Injection.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(Injection.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(Injection.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(TransactiomViewModel::class.java)) {
            return TransactiomViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}