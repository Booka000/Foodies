package com.albara.foodis.presentation.navigation

sealed class Screen(val route : String) {
    object HomeScreen : Screen("home_screen")
    object ProductDetailsScreen : Screen("product_details_screen")
    object CartScreen : Screen("cart_screen")

    companion object{
        const val LIST_AND_DETAILS_SCREENS_ROUTE = "list_and_details_screen_route"
    }
}