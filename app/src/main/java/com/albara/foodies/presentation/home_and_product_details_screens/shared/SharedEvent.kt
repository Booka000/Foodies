package com.albara.foodies.presentation.home_and_product_details_screens.shared

import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag

sealed interface SharedEvent {
    data class UpdateSelectedCategoryIndex(val index : Int) : SharedEvent
    data class UpdateCart(val product: Product) : SharedEvent
    data class UpdateTags(val tags: List<Tag>) : SharedEvent
    data object OpenBottomSheet : SharedEvent
    data object CloseBottomSheet : SharedEvent
    data object ShowSearchComponent : SharedEvent
    data object HideSearchComponent : SharedEvent
    data class IsSearching(val searchQuery : String) : SharedEvent
    data class UpdateSelectedProductId(val id : Int?) : SharedEvent
}