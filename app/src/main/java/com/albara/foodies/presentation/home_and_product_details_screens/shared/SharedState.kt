package com.albara.foodies.presentation.home_and_product_details_screens.shared

import com.albara.foodies.domain.modal.Category
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag

data class SharedState (
    val products : List<Product> = emptyList(),
    val tags : List<Tag> = emptyList(),
    val categories : List<Category> = emptyList(),
    val isLoading : Boolean = false,
    val error : String = "",
    val isSearching : Boolean = false,
    val totalInCart : Int = 0,
    val isEditingFilters : Boolean = false,
    val selectedTagsIndicator : Int = 0,
    val selectedProduct: Product? = null
)