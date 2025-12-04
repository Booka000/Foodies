package com.albara.foodies.presentation.cart_screen

import com.albara.foodies.domain.modal.Product

sealed interface CartScreenEvent {
    data class UpdateProduct(val product: Product) : CartScreenEvent
    data class DeleteProduct(val product: Product) : CartScreenEvent
}