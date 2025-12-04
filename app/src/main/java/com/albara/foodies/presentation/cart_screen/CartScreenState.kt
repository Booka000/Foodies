package com.albara.foodies.presentation.cart_screen

import com.albara.foodies.domain.modal.Product

data class CartScreenState(
    val productsInCart: List<Product> = emptyList(),
    val totalInCart : Int = 0
)