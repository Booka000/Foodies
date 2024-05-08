package com.albara.foodis.domain.repository

import com.albara.foodis.data.local.entity.CartEntity
import com.albara.foodis.domain.modal.Category
import com.albara.foodis.domain.modal.Product
import com.albara.foodis.domain.modal.Tag
import com.albara.foodis.util.Resource
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