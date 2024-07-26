package com.albara.foodies.presentation.cart_screen.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.albara.foodies.R
import com.albara.foodies.cartrow.CartRow
import com.albara.foodies.cartrow.Discount
import com.albara.foodies.counter.Counter
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.presentation.cart_screen.CartScreenEvent
import com.albara.foodies.presentation.ui.theme.Orange

@Composable
fun CartRowComponent(
    modifier: Modifier = Modifier,
    product: Product,
    onEvent : (CartScreenEvent) ->Unit
){
    CartRow(
        modifier = modifier,
        discount = if(product.priceOld == null) Discount.Off else Discount.On,
        productImage = painterResource(id = R.drawable.tom_yam),
        productName = product.name,
        productNameTextColor = MaterialTheme.colorScheme.onBackground,
        productPrice = "${product.priceCurrent} ₽",
        productPriceTextColor = MaterialTheme.colorScheme.onBackground,
        productOldPrice = "${product.priceOld} ₽",
        productOldPriceTextColor = MaterialTheme.colorScheme.onBackground,
        backgroundColor = MaterialTheme.colorScheme.background,
        counterChildren = {
            Counter(
                modifier = Modifier.width(135.dp),
                minusIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.counter_icon),
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.boxAlign(Alignment.Center, DpOffset.Zero)
                    )
                },
                plusIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.counter_icon1),
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.boxAlign(Alignment.Center, DpOffset.Zero)
                    )
                },
                minusButtonBackgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                plusButtonBackgroudColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                backgroundColor = MaterialTheme.colorScheme.background,
                itemCardCounter = product.amountInCart.toString(),
                itemCardCounterColor = MaterialTheme.colorScheme.onBackground,
                onMinusButtonClick = {
                    when(product.amountInCart - 1) {
                        0 -> onEvent(CartScreenEvent.DeleteProduct(product))
                        else -> onEvent(CartScreenEvent.UpdateProduct(product.copy(
                            amountInCart = product.amountInCart - 1
                        )))
                    }
                },
                onPlusButtonClick = {
                    onEvent(CartScreenEvent.UpdateProduct(product.copy(
                        amountInCart = product.amountInCart + 1
                    )))
                }
            )
        }
    )
}