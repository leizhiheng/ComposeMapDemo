package com.ubt.composemapdemo.ui.account.nav

import androidx.navigation.NavHostController
import com.ubt.composemapdemo.ui.account.nav.AccountDestinations.DISPLAY_ROUTE
import com.ubt.composemapdemo.ui.account.nav.AccountDestinations.SIGNUP_ACCOUNT_ROUTE
import com.ubt.composemapdemo.ui.account.nav.AccountDestinations.SIGNUP_PASSWORD_ROUTE
import com.ubt.composemapdemo.ui.account.nav.AccountDestinations.SIGN_IN_ROUTE
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_ACCOUNT
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_CAPTCHA
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.DISPLAY_SCREEN
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.MAIN_SCREEN
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.SIGNUP_ACCOUNT_SCREEN
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.SIGNUP_PASSWORD_SCREEN
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.SIGN_IN_SCREEN

object AccountScreens {
    const val DISPLAY_SCREEN = "display"
    const val SIGNUP_ACCOUNT_SCREEN = "signup_account"
    const val SIGNUP_PASSWORD_SCREEN = "signup_password"
    const val SIGN_IN_SCREEN = "sign_in"
    const val MAIN_SCREEN = "main"
}

object AccountDestinationsArgs {
    const val ARG_ACCOUNT = "account"
    const val ARG_CAPTCHA = "captcha"
}

object AccountDestinations {
    const val DISPLAY_ROUTE = DISPLAY_SCREEN
    const val SIGNUP_ACCOUNT_ROUTE = SIGNUP_ACCOUNT_SCREEN
    const val SIGNUP_PASSWORD_ROUTE = "$SIGNUP_PASSWORD_SCREEN/{$ARG_ACCOUNT}/{$ARG_CAPTCHA}"
    const val SIGN_IN_ROUTE = SIGN_IN_SCREEN
    const val MAIN_ROUTE = MAIN_SCREEN
}

class AccountNavigationActions(private val navController: NavHostController) {
    fun navigateToDisplay() {
        navController.navigate(DISPLAY_ROUTE) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    fun navigateToSignUp() {
        navController.navigate(SIGNUP_ACCOUNT_ROUTE) {
            launchSingleTop = true
        }
    }

    fun navigateToSignUpPassword(account: String, captcha: String) {
        navController.navigate("$SIGNUP_PASSWORD_SCREEN/$account/$captcha")
    }

    fun navigateToSignIn() {
        navController.navigate(SIGN_IN_ROUTE)
    }
}