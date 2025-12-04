package com.albara.foodies.data.remote.dto

import com.albara.foodies.domain.modal.Tag

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