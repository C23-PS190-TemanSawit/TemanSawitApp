package com.example.temansawit.ui.screen.auth.login

import android.content.Context
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
import com.example.temansawit.di.Preferences
import com.example.temansawit.ui.components.auth.BtnLogin
import com.example.temansawit.ui.components.auth.LoginInput
import com.example.temansawit.ui.components.auth.WelcomeLogin
import com.example.temansawit.ui.screen.ViewModelFactory

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val usernameInput = viewModel.usernameState.observeAsState(initial = "")
    val passwordInput = viewModel.passwordState.observeAsState(initial = "")
    val username = usernameInput.value
    val password = passwordInput.value

    Scaffold {paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())) {
            WelcomeLogin()
            LoginInput(
                username = username,
                password = password,
                onUsernameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                navHostController = navHostController
            )
            BtnLogin(
                navHostController = navHostController,
                onClick = {
                    viewModel.loginUser(username, password).observe(lifecycleOwner, {
                        when(it) {
                            is Result.Loading -> {
                                //loading disini
                            }
                            is Result.Success -> {
                                val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                                Preferences.saveAccessToken(it.data.accessToken, sharedPreferences)
                                Preferences.saveRefreshToken(it.data.refreshToken, sharedPreferences)
                                Preferences.setLoggedIn(sharedPreferences, true)
                                navHostController.popBackStack()
                                navHostController.navigate("home")
                            }
                            is Result.Error -> {
                                Toast.makeText(context, "Masukkan Username dan Password yang benar", Toast.LENGTH_LONG).show()
                            }
                            else -> {}
                        }
                    })
                }
            )
        }
    }
}
