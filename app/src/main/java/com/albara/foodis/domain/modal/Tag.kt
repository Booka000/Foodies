package com.albara.foodis.domain.modal

data class Tag (
    val id: Int,
    val name: String,
    val isSelected : Boolean = false
)