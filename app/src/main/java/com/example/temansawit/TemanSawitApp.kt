package com.example.temansawit

import CameraScreen
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.temansawit.di.Preferences
import com.example.temansawit.ui.components.onboarding.OnboardingUI
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.ViewModelFactory
import com.example.temansawit.ui.screen.auth.login.LoginScreen
import com.example.temansawit.ui.screen.auth.register.RegisterScreen
import com.example.temansawit.ui.screen.faq.FaqScreen
import com.example.temansawit.ui.screen.home.HomePage
import com.example.temansawit.ui.screen.profile.ProfileScreen
import com.example.temansawit.ui.screen.transaction.DetailTrxScreen
import com.example.temansawit.ui.screen.transaction.TransactionScreen
import com.example.temansawit.util.TransactionViewModel

@Composable
fun TemanSawitApp() {
    val navHostController: NavHostController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val isOnBoarded = remember { mutableStateOf(Preferences.isOnboarded(sharedPreferences)) }
    val isLogeedIn = remember { mutableStateOf(Preferences.isLoggedIn(sharedPreferences)) }
    val pref = Preferences.initPref(context, "isLoggedIn")
    val token = pref.getString("context", null).toString()
    val transactionViewModel: TransactionViewModel = viewModel(
        factory = ViewModelFactory(context)
    )


    NavHost(
        navController = navHostController,
        startDestination =
        if (isOnBoarded.value) {
            if (isLogeedIn.value && token != "") {
                "mainScreen"
            } else {
                "loginScreen"
            }
        } else {
            "onboardingScreen"
        },
    ) {
//        splashScreen(navHostController)
        onboarding(navHostController = navHostController)
        auth(navHostController = navHostController)
        main(navHostController = navHostController, transactionViewModel)
    }
}

// <<<<<<< apiRoy
 fun NavGraphBuilder.main(navHostController: NavHostController, transactionViewModel: TransactionViewModel) {
// =======
@OptIn(ExperimentalMaterialApi::class)
//fun NavGraphBuilder.main(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Home.route,
        route = "mainScreen"
    ) {
        composable(Screen.Home.route) {
            HomePage(navHostController, transactionViewModel = transactionViewModel)
        }
        composable(Screen.Transaction.route) {
            TransactionScreen(
                navigateToDetail = { transactionId ->
                    navHostController.navigate(
                        Screen.DetailTransaction.createRoute(
                            transactionId
                        )
                    )
                },
                viewModel2 =  transactionViewModel,
                navHostController = navHostController
            )
        }
//        composable(Screen.CameraScreen.route) {
//            CameraScreen(modalSheetState = )
//        }
        composable(Screen.Faq.route) {
            FaqScreen(navHostController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.DetailTransaction.route,
            arguments = listOf(navArgument("transactionId") {
                type = NavType.IntType
            }),
        ) {
            val id = it.arguments?.getInt("transactionId")
            DetailTrxScreen(
                trxId = id as Int,
                navigateBack = { navHostController.navigateUp() },
            )
        }
    }
}

//fun BottomCamera(navHostController: NavHostController) {
//
//}

fun NavGraphBuilder.onboarding(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Onboarding.route,
        route = "onboardingScreen"
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingUI(navHostController = navHostController)
        }
    }
}

fun NavGraphBuilder.auth(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Login.route,
        route = "loginScreen"
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navHostController = navHostController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navHostController = navHostController)
        }
    }
}

@Composable
fun ScaffoldApp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButtonPosition = floatingActionButtonPosition,
        isFloatingActionButtonDocked = isFloatingActionButtonDocked,
        scaffoldState = rememberScaffoldState(),
        floatingActionButton = floatingActionButton
    ) {
        Column(modifier.padding(it)) {
            content()
        }
    }
}
