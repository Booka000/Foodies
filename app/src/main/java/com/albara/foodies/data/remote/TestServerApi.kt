package com.albara.foodies.data.remote

import com.albara.foodies.data.remote.dto.CategoryDto
import com.albara.foodies.data.remote.dto.ProductDto
import com.albara.foodies.data.remote.dto.TagDto
import retrofit2.http.GET

interface TestServerApi {
    @GET("Products.json")
    suspend fun getProducts() : List<ProductDto>

    @GET("Tags.json")
    suspend fun getTags() : List<TagDto>

    @GET("Categories.json")
    suspend fun getCategories() : List<CategoryDto>
}