package com.albara.foodies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.albara.foodies.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Upsert
    suspend fun upsertProduct(productEntity: CartEntity)

    @Delete
    suspend fun deleteProduct(productEntity: CartEntity)

    @Query("SELECT * FROM cart")
    fun getProducts() : Flow<List<CartEntity>>

    @Query("DELETE FROM cart")
    suspend fun emptyTheCart()
}