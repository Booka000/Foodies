package com.albara.foodis.presentation.navigation

import android.widget.Toast
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
import com.albara.foodis.presentation.home_screen.HomeScreen
import com.albara.foodis.presentation.home_screen.HomeScreenViewModel
import com.albara.foodis.presentation.product_details_screen.ProductDetailsScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LIST_AND_DETAILS_SCREENS_ROUTE) {
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.LIST_AND_DETAILS_SCREENS_ROUTE
        ) {
            composable(Screen.HomeScreen.route) {entry ->
                val viewModel = entry.sharedViewModel<HomeScreenViewModel>(navController = navController)
                val state by viewModel.state.collectAsState()

                HomeScreen(state = state, onEvent = viewModel::onEvent) {
                    try {
                        navController.navigate(Screen.ProductDetailsScreen.route)
                    } catch (e : IllegalArgumentException) {
                        Toast.makeText(navController.context, "caught the exception", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            composable(Screen.ProductDetailsScreen.route){entry ->
                val viewModel = entry.sharedViewModel<HomeScreenViewModel>(navController = navController)
                val state by viewModel.state.collectAsState()

                ProductDetailsScreen(
                    product = state.selectedProduct,
                    onEvent = viewModel::onEvent
                )
            }
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