package com.albara.foodies.domain.use_case.fetch_from_api

import com.albara.foodies.domain.modal.Category
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag
import com.albara.foodies.domain.repository.Repository
import com.albara.foodies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDataFromApiUseCase @Inject constructor (
    private val repository: Repository
) {
    operator fun invoke() : Flow<Resource<Triple<List<Product>, List<Tag>, List<Category>>>> = flow {
        try {
            val products = repository.fetchProducts()
            val tags = repository.fetchTags()
            val categories = repository.fetchCategories()
            emit(Resource.Success(Triple(products, tags, categories)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "произошла непредвиденная ошибка"))
        } catch (e: IOException) {
            emit(Resource.Error("Не удалось подключиться к серверу.\n Проверьте подключение к Интернету."))
        }
    }
}