package com.albara.foodies.domain.modal

data class Tag (
    val id: Int,
    val name: String,
    val isSelected : Boolean = false
)