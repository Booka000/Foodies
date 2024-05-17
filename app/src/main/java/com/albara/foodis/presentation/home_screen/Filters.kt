package com.albara.foodis.presentation.home_screen

import com.albara.foodis.domain.modal.Tag

data class Filters(
    val selectedCategoryIndex : Int = 0,
    val selectedTags : List<Tag> = emptyList(),
)