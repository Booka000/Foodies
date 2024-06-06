package com.albara.foodis.presentation.products_list_and_product_details_screens.shared

import com.albara.foodis.domain.modal.Tag

data class Filters(
    val selectedCategoryIndex : Int = 0,
    val selectedTags : List<Tag> = emptyList(),
    val searchQuery : String = "",
    val selectedProductId: Int? = null
)