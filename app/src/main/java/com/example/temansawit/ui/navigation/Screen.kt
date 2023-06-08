package com.example.temansawit.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Login: Screen("login")
    object Register: Screen("register")
    object Onboarding: Screen("onboarding")
    object Home: Screen("home")

    object CameraApi: Screen("cameraapi")
    object CameraTflite: Screen("cameratflite")

    object Transaction: Screen("transaction")
    object Faq: Screen("faq") {
        fun creteRoute(faqId : Long) = "faq/$faqId"
    }
    object Profile: Screen("profile")
    object Camera: Screen("camera")
    object DetailTransaction: Screen("home/{transactionId}") {
        fun createRoute(transactionId: Int) = "home/$transactionId"
    }
}
