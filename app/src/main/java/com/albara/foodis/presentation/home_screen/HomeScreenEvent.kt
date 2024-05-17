package com.albara.foodis.presentation.home_screen

import com.albara.foodis.domain.modal.Product
import com.albara.foodis.domain.modal.Tag

sealed interface HomeScreenEvent {
    data class UpdateSelectedCategoryIndex(val index : Int) : HomeScreenEvent
    data class UpdateCart(val product: Product) : HomeScreenEvent
    data class UpdateTags(val tags: List<Tag>) : HomeScreenEvent
    data object OpenBottomSheet : HomeScreenEvent
    data object CloseBottomSheet : HomeScreenEvent
    data object ShowSearchComponent : HomeScreenEvent
    data object HideSearchComponent : HomeScreenEvent
    data class IsSearching(val searchQuery : String) : HomeScreenEvent

}