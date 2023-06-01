package com.example.temansawit.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Onboarding: Screen("onboarding")
    object Home: Screen("home")
    object Transaction: Screen("transaction")
    object Faq: Screen("faq")
    object Profile: Screen("profile")
    object DetailTransaction: Screen("home/{transactionId}") {
        fun createRoute(transactionId: Long) = "home/$transactionId"
    }
}
