package com.ubt.composemapdemo.ui.account.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ubt.composemapdemo.ui.account.display.DisplayScreen
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_ACCOUNT
import com.ubt.composemapdemo.ui.account.nav.AccountDestinationsArgs.ARG_CAPTCHA
import com.ubt.composemapdemo.ui.account.signin.SignInScreen
import com.ubt.composemapdemo.ui.account.signup.SignUpPasswordScreen
import com.ubt.composemapdemo.ui.account.signup.SignUpAccountScreen

@Composable
fun AccountNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AccountDestinations.DISPLAY_ROUTE,
    navActions: AccountNavigationActions = remember(navController) {
        AccountNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AccountDestinations.DISPLAY_ROUTE) {
            DisplayScreen(
                onUserAgreementClicked = { },
                onOpenPrivacyPolicyClicked = { },
                onSignInClicked = { navActions.navigateToSignIn() },
                onSignUpClicked = { navActions.navigateToSignUp() })
        }
        composable(AccountDestinations.SIGNUP_ACCOUNT_ROUTE) {
            SignUpAccountScreen(
                onBack = { navActions.navigateToDisplay() },
                onNextStep = { accountName, captcha ->
                    navActions.navigateToSignUpPassword(accountName, captcha)
                })
        }
        composable(
            route = AccountDestinations.SIGNUP_PASSWORD_ROUTE,
            //接收参数
            arguments = listOf(
                navArgument(ARG_ACCOUNT) {
                    type = NavType.StringType; defaultValue = ""
                },
                navArgument(ARG_CAPTCHA) {
                    type = NavType.StringType; defaultValue = ""
                }
            )
        ) {
            SignUpPasswordScreen(
                accountName = it.arguments?.getString(ARG_ACCOUNT) ?: "",
                captcha = it.arguments?.getString(ARG_CAPTCHA)?: "",
                onBack = { navActions.navigateToSignUp() },
                onSignUpSucceed = {
                    //navigate to main screen
                })
        }
        composable(AccountDestinations.SIGN_IN_ROUTE) {
            SignInScreen(
                onBack = { navActions.navigateToDisplay() },
                onSignUpClicked = { navActions.navigateToSignUp() },
                onForgetPasswordClicked = {  },
                onSignInSucceed = {  })
        }
    }
}