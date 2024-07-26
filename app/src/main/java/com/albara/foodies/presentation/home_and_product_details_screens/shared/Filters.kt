package com.albara.foodies.presentation.home_and_product_details_screens.shared

import com.albara.foodies.domain.modal.Tag

data class Filters(
    val selectedCategoryIndex : Int = 0,
    val selectedTags : List<Tag> = emptyList(),
    val searchQuery : String = "",
    val selectedProductId: Int? = null
)