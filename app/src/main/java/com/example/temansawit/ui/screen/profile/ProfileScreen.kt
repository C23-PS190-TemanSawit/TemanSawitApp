package com.example.temansawit.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.temansawit.R
import com.example.temansawit.ScaffoldApp
import com.example.temansawit.ui.components.navigation.BottomBar
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.theme.Green700

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ScaffoldApp(
        bottomBar = {
            if (currentRoute != Screen.DetailTransaction.route) {
                BottomBar(navHostController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (currentRoute != Screen.DetailTransaction.route) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { },
                    backgroundColor = Green700,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_camera_alt_24),
                        contentDescription = "Deteksi Sawit"
                    )
                }
            }
        },

        content = {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Profile Screen")
            }
        }
    )
}