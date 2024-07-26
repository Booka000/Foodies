package com.albara.foodies.domain.use_case.manege_date_in_local

import com.albara.foodies.data.local.entity.CartEntity
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccessDataInLocalUseCase @Inject constructor(
    private val repository: Repository
) {
    fun getCartProducts(): Flow<List<Product>> {
        return repository.getCartProducts().map { it.map {product ->  product.toProduct() } }
    }

    suspend fun upsertProduct(cartEntity: CartEntity) {
        repository.upsertProduct(cartEntity)
    }

    suspend fun deleteProduct(cartEntity: CartEntity) {
        repository.deleteProduct(cartEntity)
    }

    suspend fun emptyTheCart() {
        repository.emptyTheCart()
    }
}