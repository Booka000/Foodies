package com.albara.foodies.presentation.navigation

sealed class Screen(val route : String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object ProductDetailsScreen : Screen("product_details_screen")
    object CartScreen : Screen("cart_screen")

    companion object{
        const val HOME_AND_DETAILS_SCREENS_ROUTE = "home_and_details_screen_route"
    }
}