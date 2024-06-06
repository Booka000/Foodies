package com.albara.foodis.presentation.products_list_and_product_details_screens.shared

import com.albara.foodis.domain.modal.Category
import com.albara.foodis.domain.modal.Product
import com.albara.foodis.domain.modal.Tag

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