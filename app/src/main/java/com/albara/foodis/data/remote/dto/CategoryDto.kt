package com.albara.foodis.data.remote.dto

import com.albara.foodis.domain.modal.Category

data class CategoryDto(
    val id: Int,
    val name: String
) {
    fun toCategory() : Category {
        return Category(
            id,
            name
        )
    }
}