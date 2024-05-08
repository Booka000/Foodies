package com.albara.foodis.presentation.home_screen

import com.albara.foodis.domain.modal.Product

sealed interface HomeScreenEvent {
    data class UpdateCategoryId(val categoryId: Int?) : HomeScreenEvent
    data class UpdateCart(val product: Product) : HomeScreenEvent
    object OpenBottomSheet : HomeScreenEvent
    object CloseBottomSheet : HomeScreenEvent
}