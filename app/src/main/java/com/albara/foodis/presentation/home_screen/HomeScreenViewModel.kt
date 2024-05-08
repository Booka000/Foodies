package com.albara.foodis.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albara.foodis.domain.modal.Product
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
                if(_state.isSearching)
                    _state.copy(
                        products = mergeLists(apiData.data.first, cart).filter {
                                product -> product.name.contains(_state.searchQuery) },
                        tags = apiData.data.second,
                        categories = apiData.data.third,
                        totalInCart = cart.sumOf { it.amountInCart * it.priceCurrent }
                    )
                else
                    _state.copy(
                        products = mergeLists(apiData.data.first, cart).filter {
                                product ->  filterProduct(product, filters)},
                        tags = apiData.data.second,
                        categories = apiData.data.third,
                        totalInCart = cart.sumOf { it.amountInCart * it.priceCurrent }
                    )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState(isLoading = true))

    private fun filterProduct(product : Product, filters: Filters) : Boolean {
        return if(filters.categoryId != null)
            product.categoryId == filters.categoryId
                    && product.tagIds.containsAll(filters.tagsIds)
        else
            product.tagIds.containsAll(filters.tagsIds)
    }

    private fun mergeLists(
        first : List<Product>,
        second : List<Product>
    ) : List<Product> {
        val resultMap = mutableMapOf<Int, Product>()

        // Add objects from list1 to resultMap
        first.forEach { obj ->
            resultMap[obj.id] = obj
        }

        // Add objects from list2 to resultMap, replacing existing objects if id matches
        second.forEach { obj ->
            resultMap[obj.id] = obj
        }

        // Convert resultMap back to a list
        return resultMap.values.toList()
    }

    fun onEvent(event : HomeScreenEvent) {
        when(event){
            is HomeScreenEvent.UpdateCategoryId -> {
                filters.update { it.copy(categoryId = event.categoryId)}
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
        }
    }
}