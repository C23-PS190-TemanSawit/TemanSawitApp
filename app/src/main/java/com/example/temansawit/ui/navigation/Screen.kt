package com.example.temansawit.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Login: Screen("login")
    object Register: Screen("register")
    object Onboarding: Screen("onboarding")
    object Home: Screen("home")
    object Transaction: Screen("transaction")
    object Faq: Screen("faq") {
        fun creteRoute(faqId : Long) = "faq/$faqId"
    }
    object Profile: Screen("profile")
    object AboutUs: Screen("about")
    object ChangePassword: Screen("about")
    object DetailTransaction: Screen("home/{transactionId}") {
        fun createRoute(transactionId: Int) = "home/$transactionId"
    }
}
