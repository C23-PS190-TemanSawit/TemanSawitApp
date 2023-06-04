package com.example.temansawit.ui.screen.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.temansawit.ui.components.auth.LoginInput
import com.example.temansawit.ui.components.auth.WelcomeLogin

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Scaffold {
        Column(
            modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
            WelcomeLogin()
            LoginInput(navHostController = navHostController)
        }
    }
}
