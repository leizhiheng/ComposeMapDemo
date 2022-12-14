package com.ubt.composemapdemo.ui.account.nav

import androidx.navigation.NavHostController
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_ACCOUNT
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_CAPTCHA
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.ROUTE_MAIN
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.ROUTE_SIGNUP_ACCOUNT
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.ROUTE_SIGNUP_PASSWORD
import com.ubt.composemapdemo.ui.account.nav.AccountScreens.ROUTE_SIGN_IN

object AccountScreens {
    const val ROUTE_SIGNUP_ACCOUNT = "signup_account"
    const val ROUTE_SIGNUP_PASSWORD = "signup_password"
    const val ROUTE_SIGN_IN = "sign_in"
    const val ROUTE_MAIN = "main"
}

object AccountDestinationsArgs {
    const val ARG_ACCOUNT = "account"
    const val ARG_PASSWORD = "password"
    const val ARG_CAPTCHA = "captcha"
}

object AccountDestinations {
    const val ROUTE_SIGNUP_ACCOUNT_ROUTE = ROUTE_SIGNUP_ACCOUNT
    const val ROUTE_SIGNUP_PASSWORD_ROUTE = "$ROUTE_SIGNUP_PASSWORD/$ARG_ACCOUNT/$ARG_CAPTCHA"
    const val ROUTE_SIGN_IN_ROUTE = ROUTE_SIGN_IN
    const val ROUTE_MAIN_ROUTE = ROUTE_MAIN
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateToSignUp() {

    }
}