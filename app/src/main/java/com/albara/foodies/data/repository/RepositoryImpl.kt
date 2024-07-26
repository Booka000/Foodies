package com.albara.foodies.data.repository

import com.albara.foodies.data.local.CartDao
import com.albara.foodies.data.local.entity.CartEntity
import com.albara.foodies.data.remote.TestServerApi
import com.albara.foodies.domain.modal.Category
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag
import com.albara.foodies.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api : TestServerApi,
    private val dao: CartDao
): Repository {
    override suspend fun fetchProducts(): List<Product> {
        return api.getProducts().map { it.toProduct() }
    }

    override suspend fun fetchTags(): List<Tag> {
        return api.getTags().map { it.toTag() }
    }

    override suspend fun fetchCategories(): List<Category> {
        return api.getCategories().map { it.toCategory() }
    }


    override fun getCartProducts(): Flow<List<CartEntity>> {
        return dao.getProducts()
    }

    override suspend fun upsertProduct(cartEntity: CartEntity) {
        dao.upsertProduct(cartEntity)
    }

    override suspend fun deleteProduct(cartEntity: CartEntity) {
        dao.deleteProduct(cartEntity)
    }

    override suspend fun emptyTheCart() {
        dao.emptyTheCart()
    }

}