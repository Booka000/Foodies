package com.albara.foodis.data.repository

import com.albara.foodis.data.local.CartDao
import com.albara.foodis.data.local.entity.CartEntity
import com.albara.foodis.data.remote.TestServerApi
import com.albara.foodis.domain.modal.Category
import com.albara.foodis.domain.modal.Product
import com.albara.foodis.domain.modal.Tag
import com.albara.foodis.domain.repository.Repository
import com.albara.foodis.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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