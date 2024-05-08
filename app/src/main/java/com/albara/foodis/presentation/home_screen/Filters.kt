package com.albara.foodis.presentation.home_screen


data class Filters(
    val categoryId : Int? = null,
    val tagsIds : List<Int> = emptyList(),
)