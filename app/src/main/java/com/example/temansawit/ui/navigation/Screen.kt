package com.example.temansawit.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Transaction: Screen("transaction")
    object Faq: Screen("faq") {
        fun creteRoute(faqId : Long) = "faq/$faqId"
    }
    object Profile: Screen("profile")
    object DetailTransaction: Screen("home/{transactionId}") {
        fun createRoute(transactionId: Long) = "home/$transactionId"
    }
}
