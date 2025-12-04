package com.albara.foodies.presentation.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albara.foodies.domain.use_case.manege_date_in_local.AccessDataInLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    val cart : AccessDataInLocalUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CartScreenState())
    val state : State<CartScreenState> = _state

    init {
        updateState()
    }

    private fun updateState(){
        cart.getCartProducts().onEach { products ->
            _state.value = CartScreenState(
                productsInCart = products,
                totalInCart = products.sumOf { it.amountInCart * it.priceCurrent }
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CartScreenEvent) {
        when(event){
            is CartScreenEvent.UpdateProduct -> {
                viewModelScope.launch {
                    cart.upsertProduct(event.product.toCartEntity())
                }
            }

            is CartScreenEvent.DeleteProduct -> {
                viewModelScope.launch {
                    cart.deleteProduct(event.product.toCartEntity())
                }
            }
        }
    }
}