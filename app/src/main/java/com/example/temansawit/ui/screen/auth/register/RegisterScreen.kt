package com.example.temansawit.ui.screen.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.temansawit.data.Result
import com.example.temansawit.ui.components.auth.BtnRegister
import com.example.temansawit.ui.components.auth.RegisterInput
import com.example.temansawit.ui.components.auth.WelcomeRegister
import com.example.temansawit.ui.screen.ViewModelFactory

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val usernameInput = viewModel.usernameState.observeAsState(initial = "")
    val emailInput = viewModel.emailState.observeAsState(initial = "")
    val passwordInput = viewModel.passwordState.observeAsState(initial = "")
    val passwordKonfirmasiInput = viewModel.confirmPasswordState.observeAsState(initial = "")
    val username = usernameInput.value
    val email = emailInput.value
    val password = passwordInput.value
    val konfirmasiPassword = passwordKonfirmasiInput.value

    Scaffold {
        Column(
            modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
            WelcomeRegister()
            RegisterInput(
                username = username,
                email = email,
                password = password,
                konfirmasiPassword = konfirmasiPassword,
                onUsernameChange = viewModel::onUsernameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onKonfirmasiPasswordChange = viewModel::onConfirmPasswordChange
            )
            BtnRegister(
                navHostController = navHostController,
                onClick = {
                    viewModel.registerUser(username, email, password, konfirmasiPassword).observe(lifecycleOwner, {register ->
                        when (register) {
                            is Result.Loading -> {
                                //result loading
                            }
                            is Result.Success -> {
                                Toast.makeText(context, register.data.message, Toast.LENGTH_LONG).show()
                                navHostController.popBackStack()
                                navHostController.navigate("login")
                            }
                            is Result.Error -> {
                                Toast.makeText(context, "Masukkan data anda dengan benar", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            )
        }
    }
}