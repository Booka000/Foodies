package com.albara.foodis.data.remote

import com.albara.foodis.data.remote.dto.CategoryDto
import com.albara.foodis.data.remote.dto.ProductDto
import com.albara.foodis.data.remote.dto.TagDto
import retrofit2.http.GET

interface TestServerApi {
    @GET("Products.json")
    suspend fun getProducts() : List<ProductDto>

    @GET("Tags.json")
    suspend fun getTags() : List<TagDto>

    @GET("Categories.json")
    suspend fun getCategories() : List<CategoryDto>
}