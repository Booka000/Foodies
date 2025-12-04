package com.albara.foodies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albara.foodies.domain.modal.Product

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = false)
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
) {
    fun toProduct() : Product {
        return Product(
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
