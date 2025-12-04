package com.albara.foodies.domain.repository

import com.albara.foodies.data.local.entity.CartEntity
import com.albara.foodies.domain.modal.Category
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun fetchProducts() : List<Product>

    suspend fun fetchTags() : List<Tag>

    suspend fun fetchCategories() : List<Category>

    fun getCartProducts(): Flow<List<CartEntity>>

    suspend fun upsertProduct(cartEntity: CartEntity)

    suspend fun deleteProduct(cartEntity: CartEntity)

    suspend fun emptyTheCart()
}