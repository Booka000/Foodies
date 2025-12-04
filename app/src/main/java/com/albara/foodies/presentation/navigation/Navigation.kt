package com.albara.foodies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.albara.foodies.presentation.cart_screen.CartScreen
import com.albara.foodies.presentation.cart_screen.CartScreenViewModel
import com.albara.foodies.presentation.home_and_product_details_screens.home_screen.HomeScreen
import com.albara.foodies.presentation.home_and_product_details_screens.product_details_screen.ProductDetailsScreen
import com.albara.foodies.presentation.home_and_product_details_screens.shared.SharedViewModel
import com.albara.foodies.presentation.splash_screen.SplashScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(Screen.HOME_AND_DETAILS_SCREENS_ROUTE)
                }
            )
        }
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.HOME_AND_DETAILS_SCREENS_ROUTE
        ) {
            composable(Screen.HomeScreen.route) {entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController = navController)
                val state by viewModel.state.collectAsState()

                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigate = navController::navigate
                )
            }

            composable(Screen.ProductDetailsScreen.route){entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController = navController)
                val state by viewModel.state.collectAsState()

                ProductDetailsScreen(
                    product = state.selectedProduct,
                    onEvent = viewModel::onEvent,
                    onNavigateBack = navController::popBackStack
                )
            }
        }
        composable(Screen.CartScreen.route){entry->
            val viewModel = entry.sharedViewModel<CartScreenViewModel>(navController = navController)
            val state by viewModel.state
            CartScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateBack = navController::popBackStack
            )
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}