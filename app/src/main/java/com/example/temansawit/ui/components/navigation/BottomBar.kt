package com.example.temansawit.ui.components.navigation

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.temansawit.R
import com.example.temansawit.ui.navigation.NavigationItem
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.theme.GreenPrimary

@Composable
fun BottomBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home_nav),
                icon = painterResource(id = R.drawable.baseline_home_24),
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.trx_nav),
                icon = painterResource(id = R.drawable.baseline_quiz_24),
                screen = Screen.Transaction
            ),
            NavigationItem(
                title = stringResource(id = R.string.faq_nav),
                icon = painterResource(id = R.drawable.baseline_quiz_24),
                screen = Screen.Faq
            ),
            NavigationItem(
                title = stringResource(id = R.string.profile_nav),
                icon = painterResource(id = R.drawable.baseline_person_24),
                screen = Screen.Profile
            ),
        )
        BottomAppBar(
            modifier = modifier,
            backgroundColor = Color.White,
            cutoutShape = CircleShape
        ) {
            navigationItems.forEachIndexed { index, item ->
                if (index == 2) {
                    BottomNavigationItem(
                        selected = false,
                        onClick = { },
                        icon = {},
                        enabled = false
                    )
                }
                BottomNavigationItem(
//                    selected = currentRoute == item.screen.route,
                    selected = currentRoute?.hierarchy?.any { it.route == item.screen.route } == true,
                    selectedContentColor = GreenPrimary,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        navHostController.navigate(item.screen.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) }
                )
            }
        }
    }
}