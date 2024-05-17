package com.albara.foodis.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albara.foodis.domain.modal.Product
import com.albara.foodis.domain.modal.Tag
import com.albara.foodis.domain.use_case.fetch_from_api.GetDataFromApiUseCase
import com.albara.foodis.domain.use_case.manege_date_in_local.AccessDataInLocalUseCase
import com.albara.foodis.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    dataFromApi : GetDataFromApiUseCase,
    private val cart : AccessDataInLocalUseCase
) : ViewModel() {

    private val _state  = MutableStateFlow(HomeScreenState())
    private val filters = MutableStateFlow(Filters())

    val state = combine(_state,dataFromApi(), cart.getCartProducts(), filters) {
            _state, apiData, cart, filters ->
        when(apiData) {
            is Resource.Error -> _state.copy(error = apiData.message)
            is Resource.Success -> {
                if(_state.isSearching) {
                    val products = apiData.data.first.mergeWithoutDuplicates(cart, Product::id)
                            .applySearch(_state.searchQuery)

                    val error = if (products.isNotEmpty()) ""
                    else if(_state.searchQuery.isNotBlank())
                        "Ничего не нашлось :("
                    else "Введите название блюда,\nкоторое ищете"

                    _state.copy(
                        products = products,
                        tags = apiData.data.second,
                        categories = apiData.data.third,
                        totalInCart = cart.sumOf { it.amountInCart * it.priceCurrent },
                        error = error
                    )
                }
                else {
                    val selectedCategoryId = apiData.data.third[filters.selectedCategoryIndex].id

                    val products = apiData.data.first.mergeWithoutDuplicates(cart, Product::id)
                        .applyFilters(selectedCategoryId, filters.selectedTags.map { it.id })

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
                        selectedCategoryId = selectedCategoryId
                    )
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeScreenState(isLoading = true)
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

    fun onEvent(event : HomeScreenEvent) {
        when(event){
            is HomeScreenEvent.UpdateSelectedCategoryIndex -> {
                filters.update { it.copy(selectedCategoryIndex = event.index)}
            }

            is HomeScreenEvent.UpdateCart -> {
                viewModelScope.launch {
                    if(event.product.amountInCart == 0)
                        cart.deleteProduct(event.product.toCartEntity())
                    else {
                        cart.upsertProduct(event.product.toCartEntity())
                    }
                }
            }

            is HomeScreenEvent.CloseBottomSheet -> {
                _state.update { it.copy(isEditingFilters = false) }
            }

            is HomeScreenEvent.OpenBottomSheet -> {
                _state.update { it.copy(isEditingFilters = true) }
            }

            is HomeScreenEvent.UpdateTags -> {
                filters.update { it.copy(selectedTags = event.tags) }
            }

            HomeScreenEvent.ShowSearchComponent -> {
                _state.update { it.copy(isSearching = true) }
            }

            HomeScreenEvent.HideSearchComponent -> {
                _state.update { it.copy(isSearching = false, searchQuery = "") }
            }

            is HomeScreenEvent.IsSearching -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }
        }
    }
}