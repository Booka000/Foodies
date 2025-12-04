package com.albara.foodies.domain.modal

import com.albara.foodies.data.local.entity.CartEntity


data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String,
    val energyPer100Grams: Double,
    val proteinsPer100Grams: Double,
    val fatsPer100Grams: Double,
    val carbohydratesPer100Grams: Double,
    val tagIds: List<Int>,
    val amountInCart : Int = 0
)  {
    fun toCartEntity() : CartEntity {
        return CartEntity(
            id,
            categoryId,
            name,
            description,
            image,
            priceCurrent,
            priceOld,
            measure,
            measureUnit,
            energyPer100Grams,
            proteinsPer100Grams,
            fatsPer100Grams,
            carbohydratesPer100Grams,
            tagIds,
            amountInCart
        )
    }
}
