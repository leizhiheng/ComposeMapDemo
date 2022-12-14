package com.ubt.composemapdemo.ui.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubt.composemapdemo.ui.account.signup.SignupScreen
import com.ubt.composemapdemo.ui.device.DeviceViewModel
import com.ubt.composemapdemo.ui.device.MapVirtualWallScreen
import com.ubt.composemapdemo.ui.device.AutoCleanScreen
import com.ubt.composemapdemo.ui.map.MapDisplayScreen
import com.ubt.composemapdemo.ui.map.MapViewModel

private object NavigationDestinations{
    const val ROUTE_AUTO_CLEAN = "auto_clean"
    const val ROUTE_MAP_DISPLAY = "map_display"
    const val ROUTE_MAP_VIRTUAL_WALL = "map_virtual_wall"


}

@Composable
fun Navigator(deviceViewModel: DeviceViewModel = viewModel()) {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationDestinations.ROUTE_AUTO_CLEAN) {
        composable(NavigationDestinations.ROUTE_AUTO_CLEAN) {
            AutoCleanScreen()
        }
        composable(NavigationDestinations.ROUTE_MAP_DISPLAY) {
            MapDisplayScreen()
        }
        composable(NavigationDestinations.ROUTE_MAP_VIRTUAL_WALL) {
            MapVirtualWallScreen()
        }
    }

    Router.init(navController)
}

class Router {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var navController: NavController

        fun init(navController: NavController) {
            this.navController = navController
        }

        fun toDeviceScreen() {
            navController.navigate(NavigationDestinations.ROUTE_AUTO_CLEAN)
        }

        fun toMapDisplayScreen() {
            navController.navigate(NavigationDestinations.ROUTE_MAP_DISPLAY)
        }

        fun toMapVirtualWallScreen() {
            navController.navigate(NavigationDestinations.ROUTE_MAP_VIRTUAL_WALL)
        }

        fun popBack() {
            navController.popBackStack()
        }

        fun popBack(route: String, inclusive: Boolean) {
            navController.popBackStack(route, inclusive)
        }
    }
}