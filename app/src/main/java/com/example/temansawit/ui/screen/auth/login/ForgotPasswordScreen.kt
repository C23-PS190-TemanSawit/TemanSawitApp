package com.example.temansawit.ui.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.temansawit.data.Result
import com.example.temansawit.ui.components.auth.*
import com.example.temansawit.ui.screen.ViewModelFactory

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val usernameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }
    val confPasswordInput = remember { mutableStateOf("") }
    val username = usernameInput.value
    val password = passwordInput.value
    val confPassword = confPasswordInput.value

    Scaffold {
        Column(
            modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
            WelcomeForgot()
            ForgotPasswordInput(
                username = username,
                newPassword = password,
                confPassword = confPassword,
                onUsernameChange = { usernameInput.value  = it},
                onPasswordChange = { passwordInput.value = it},
                onConfPasswordChange = { confPasswordInput.value = it }
            )
            BtnForgotPassword(
                navHostController = navHostController,
                onClick = {
                    viewModel.forgotPassword(username, password, confPassword).observe(lifecycleOwner, {
                        when(it) {
                            is Result.Loading -> {
                                //loading disini
                            }
                            is Result.Success -> {
                                Toast.makeText(context, it.data.message, Toast.LENGTH_LONG).show()
                                navHostController.popBackStack()
                                navHostController.navigate("login")
                            }
                            is Result.Error -> {
                                Toast.makeText(context, "Masukkan Username dan Password dengan benar", Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    })
                }
            )
        }
    }
}
