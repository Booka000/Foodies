package com.albara.foodies.data.remote.dto

import com.albara.foodies.domain.modal.Product

data class ProductDto(
    val id: Int,
    val category_id: Int,
    val name: String,
    val description: String,
    val image: String,
    val price_current: Int,
    val price_old: Int?,
    val measure: Int,
    val measure_unit: String,
    val energy_per_100_grams: Double,
    val proteins_per_100_grams: Double,
    val fats_per_100_grams: Double,
    val carbohydrates_per_100_grams: Double,
    val tag_ids: List<Int>
) {
    fun toProduct() : Product {
        return Product(
            id,
            category_id,
            name,
            description,
            image,
            price_current,
            price_old,
            measure,
            measure_unit,
            energy_per_100_grams,
            proteins_per_100_grams,
            fats_per_100_grams,
            carbohydrates_per_100_grams,
            tag_ids
        )
    }
}