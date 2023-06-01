package com.example.temansawit.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Transaction: Screen("transaction")
    object Faq: Screen("faq")
    object Profile: Screen("profile")
    object AboutUs: Screen("about")
    object ChangePassword: Screen("about")
    object DetailTransaction: Screen("home/{transactionId}") {
        fun createRoute(transactionId: Long) = "home/$transactionId"
    }
}
