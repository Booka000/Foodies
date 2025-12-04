package com.albara.foodies.presentation.home_and_product_details_screens.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.albara.foodies.R
import com.albara.foodies.buttonaddtocart.ButtonAddToCart
import com.albara.foodies.buttonaddtocart.Discount
import com.albara.foodies.counter.Counter
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.itemcard.InCart
import com.albara.foodies.itemcard.ItemCard
import com.albara.foodies.itemtag.Itemtag
import com.albara.foodies.itemtag.Type
import com.albara.foodies.presentation.home_and_product_details_screens.shared.SharedEvent
import com.albara.foodies.presentation.ui.theme.Orange

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    onEvent : (SharedEvent) -> Unit
) {
    ItemCard(
        modifier = modifier.clickable {
            onEvent(SharedEvent.UpdateSelectedProductId(product.id))
        },
        inCart = if (product.amountInCart != 0) InCart.On else InCart.Off,
        itemName = product.name,
        itemWeight = "${product.measure}  ${product.measureUnit}",
        itemNameColor = MaterialTheme.colorScheme.onSurface,
        itemWeightColor = MaterialTheme.colorScheme.onSurface,
        imageAndTags = {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.tom_yam),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp)
                        .align(Alignment.TopStart)
                ) {
                    product.tagIds.onEach {id ->
                        when (id) {
                            2 -> TagVegan()
                            4 -> TagSpicy()
                        }
                    }

                    product.priceOld?.let {
                        TagDiscount()
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        addToCardButton = {
            ButtonAddToCartComponent(modifier = Modifier.fillMaxSize(),
                product = product) {product ->
                onEvent(SharedEvent.UpdateCart(product))
            }
        },
        counter = {
            CounterComponent(modifier = Modifier.fillMaxSize(),
                product = product) {product ->
                onEvent(SharedEvent.UpdateCart(product))
            }
        }
    )
}

@Composable
fun TagSpicy() {
    Box(modifier = Modifier.size(20.dp)) {
        Itemtag(
            modifier = Modifier
                .clip(CircleShape),
            type = Type.Spicy,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_spicy),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun TagVegan() {
    Box(modifier = Modifier.size(20.dp)) {
        Itemtag(
            modifier = Modifier
                .clip(CircleShape),
            type = Type.Vegan,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vegan),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun TagDiscount() {
    Box(modifier = Modifier.size(20.dp)) {
        Itemtag(
            modifier = Modifier
                .clip(CircleShape),
            type = Type.Discount,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_discount),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
fun CounterComponent(
    modifier: Modifier = Modifier,
    product: Product,
    onAmountModified : (Product) -> Unit
) {
    Counter(
        modifier = modifier,
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
        minusButtonBackgroundColor = MaterialTheme.colorScheme.background,
        plusButtonBackgroudColor = MaterialTheme.colorScheme.background,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        itemCardCounter = product.amountInCart.toString(),
        itemCardCounterColor = MaterialTheme.colorScheme.onBackground,
        onMinusButtonClick = {
            onAmountModified(product.copy(amountInCart = product.amountInCart - 1))
        },
        onPlusButtonClick = {
            onAmountModified(product.copy(amountInCart = product.amountInCart + 1))
        }
    )
}

@Composable
fun ButtonAddToCartComponent(
    modifier: Modifier = Modifier,
    product: Product,
    onAmountModified : (Product) -> Unit
) {
    ButtonAddToCart (
        modifier = modifier,
        price = "${product.priceCurrent} ₽",
        priceBefore = "${product.priceOld} ₽",
        priceTextColor = MaterialTheme.colorScheme.onBackground,
        priceBeforeTextColor = MaterialTheme.colorScheme.onBackground,
        buttonBackgroundColor = MaterialTheme.colorScheme.background,
        discount = if (product.priceOld != null) Discount.On else Discount.Off,
        onClick = {
            onAmountModified(product.copy(amountInCart = product.amountInCart + 1))
        }
    )
}