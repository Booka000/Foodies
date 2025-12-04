package com.albara.foodies.presentation.home_and_product_details_screens.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.domain.modal.Tag
import com.albara.foodies.domain.use_case.fetch_from_api.GetDataFromApiUseCase
import com.albara.foodies.domain.use_case.manege_date_in_local.AccessDataInLocalUseCase
import com.albara.foodies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    dataFromApi : GetDataFromApiUseCase,
    private val cart : AccessDataInLocalUseCase
) : ViewModel() {

    private val _state  = MutableStateFlow(SharedState())
    private val filters = MutableStateFlow(Filters())

    val state = combine(_state,dataFromApi(), cart.getCartProducts(), filters) {
            _state, apiData, cart, filters ->
        when(apiData) {
            is Resource.Error -> _state.copy(error = apiData.message)
            is Resource.Success -> {
                if(_state.isSearching) {
                    val products = apiData.data.first.mergeWithoutDuplicates(cart, Product::id)
                        .applySearch(filters.searchQuery)

                    val product = when(filters.selectedProductId) {
                        null -> null
                        else -> products.find{product -> product.id == filters.selectedProductId }
                    }

                    val error = if (products.isNotEmpty()) ""
                    else if(filters.searchQuery.isNotBlank())
                        "Ничего не нашлось :("
                    else "Введите название блюда,\nкоторое ищете"

                    _state.copy(
                        products = products,
                        tags = apiData.data.second,
                        categories = apiData.data.third,
                        totalInCart = cart.sumOf { it.amountInCart * it.priceCurrent },
                        error = error,
                        selectedProduct = product
                    )
                }
                else {
                    val selectedCategoryId = apiData.data.third[filters.selectedCategoryIndex].id

                    val products = apiData.data.first.mergeWithoutDuplicates(cart, Product::id)
                        .applyFilters(
                            selectedCategoryId,
                            filters.selectedTags.map { it.id }
                        )

                    val product = when(filters.selectedProductId) {
                        null -> null
                        else -> products.find{product -> product.id == filters.selectedProductId }
                    }

                    val tags = apiData.data.second
                        .mergeWithoutDuplicates(filters.selectedTags, Tag::id)

                    val error = if (products.isEmpty()) "Таких блюд нет :(\nПопробуйте изменить фильтры"
                    else ""

                    _state.copy(
                        products = products,
                        tags = tags,
                        categories = apiData.data.third,
                        totalInCart = cart.sumOf { it.amountInCart * it.priceCurrent },
                        selectedTagsIndicator = filters.selectedTags.size,
                        error = error,
                        selectedProduct = product
                    )
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SharedState(isLoading = true)
    )

    private fun <T, K> List<T>.mergeWithoutDuplicates(
        second: List<T>,
        keySelector: (T) -> K
    ) : List<T>
    {
        val map1 = this.associateBy(keySelector)
        val map2 = second.associateBy(keySelector)
        return (map1 + map2).values.toList()
    }

    private fun List<Product>.applySearch(
        searchQuery: String
    ) : List<Product>{
        return if(searchQuery.isBlank())
            emptyList()
        else
            this.filter { product -> product.name.contains(searchQuery, ignoreCase = true) }
    }

    private fun List<Product>.applyFilters(
        categoryId: Int,
        selectedTags : List<Int>
    ) : List<Product>{
        val result = this.filter { product -> product.categoryId == categoryId &&
                    product.tagIds.containsAll(selectedTags)}
        return result
    }

    fun onEvent(event : SharedEvent) {
        when(event){
            is SharedEvent.UpdateSelectedCategoryIndex -> {
                filters.update { it.copy(selectedCategoryIndex = event.index)}
            }

            is SharedEvent.UpdateCart -> {
                viewModelScope.launch {
                    if(event.product.amountInCart == 0)
                        cart.deleteProduct(event.product.toCartEntity())
                    else {
                        cart.upsertProduct(event.product.toCartEntity())
                    }
                }
            }

            is SharedEvent.CloseBottomSheet -> {
                _state.update { it.copy(isEditingFilters = false) }
            }

            is SharedEvent.OpenBottomSheet -> {
                _state.update { it.copy(isEditingFilters = true) }
            }

            is SharedEvent.UpdateTags -> {
                filters.update { it.copy(selectedTags = event.tags) }
            }

            SharedEvent.ShowSearchComponent -> {
                _state.update { it.copy(isSearching = true) }
            }

            SharedEvent.HideSearchComponent -> {
                _state.update { it.copy(isSearching = false)}
                filters.update { it.copy(searchQuery = "") }
            }

            is SharedEvent.IsSearching -> {
                filters.update { it.copy(searchQuery = event.searchQuery) }
            }

            is SharedEvent.UpdateSelectedProductId -> {
                filters.update { it.copy(selectedProductId = event.id) }
            }
        }
    }
}