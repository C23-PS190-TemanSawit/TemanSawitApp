package com.example.temansawit.ui.screen.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.temansawit.ui.components.auth.RegisterInput
import com.example.temansawit.ui.components.auth.WelcomeRegister

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Scaffold {
        Column(
            modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
            WelcomeRegister()
            RegisterInput(navHostController = navHostController)
        }
    }
}