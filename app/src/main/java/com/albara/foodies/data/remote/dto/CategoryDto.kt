package com.albara.foodies.data.remote.dto

import com.albara.foodies.domain.modal.Category

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