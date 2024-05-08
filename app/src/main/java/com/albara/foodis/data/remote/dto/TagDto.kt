package com.albara.foodis.data.remote.dto

import com.albara.foodis.domain.modal.Tag

data class TagDto(
    val id: Int,
    val name: String
) {
    fun toTag() : Tag {
        return Tag(
            id,
            name
        )
    }
}