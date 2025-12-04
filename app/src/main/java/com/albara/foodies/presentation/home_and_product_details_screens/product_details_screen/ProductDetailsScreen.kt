package com.albara.foodies.presentation.home_and_product_details_screens.product_details_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.albara.foodies.R
import com.albara.foodies.buttonbasic.ButtonBasic
import com.albara.foodies.buttonbasic.IconLeft
import com.albara.foodies.counter.Counter
import com.albara.foodies.domain.modal.Product
import com.albara.foodies.listitem.ListItem
import com.albara.foodies.presentation.home_and_product_details_screens.shared.SharedEvent
import com.albara.foodies.presentation.ui.theme.Orange


@Composable
fun ProductDetailsScreen(
    product: Product?,
    onEvent: (SharedEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 30.dp)) {
        if (product == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        Box(Modifier.padding(start = 20.dp, top = 20.dp).clickable {
            onNavigateBack()
        }){
            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = null,
                tint = Orange
            )
        }

        Column(Modifier.fillMaxWidth()) {
            Box (
                Modifier
                    .weight(1f)
                    .fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Image(
                    painter = painterResource(id = R.drawable.tom_yam),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = product?.name ?: "",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = product?.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.size(10.dp))
            ListItem(
                backgroundColor = MaterialTheme.colorScheme.background,
                name = "Вес",
                value = "${product?.measure} ${product?.measureUnit}",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
            ListItem(
                backgroundColor = MaterialTheme.colorScheme.background,
                name = "Энерг. ценность",
                value = "${product?.energyPer100Grams} ккал",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
            ListItem(
                backgroundColor = MaterialTheme.colorScheme.background,
                name = "Белки",
                value = "${product?.proteinsPer100Grams} ${product?.measureUnit}",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
            ListItem(
                backgroundColor = MaterialTheme.colorScheme.background,
                name = "Жиры",
                value = "${product?.fatsPer100Grams} ${product?.measureUnit}",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
            ListItem(
                backgroundColor = MaterialTheme.colorScheme.background,
                name = "Углеводы",
                value = "${product?.carbohydratesPer100Grams} ${product?.measureUnit}",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(20.dp)){
                if(product!!.amountInCart == 0) {
                    ButtonBasic(
                        modifier = Modifier.fillMaxWidth(),
                        iconLeft = IconLeft.On,
                        text = "В корзину за ${product.priceCurrent} ₽",
                        textColor = Color.White,
                        cartIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.cart),
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        onClick = {
                            onEvent(SharedEvent.UpdateCart(product.copy(amountInCart = product.amountInCart + 1)))
                        }
                    )
                } else {
                    Counter(
                        modifier = Modifier.fillMaxWidth(),
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
                        itemCardCounter = product.amountInCart.toString(),
                        itemCardCounterColor = MaterialTheme.colorScheme.onBackground,
                        onMinusButtonClick = {
                            onEvent(SharedEvent.UpdateCart(product.copy(amountInCart = product.amountInCart - 1)))
                        },
                        onPlusButtonClick = {
                            onEvent(SharedEvent.UpdateCart(product.copy(amountInCart = product.amountInCart + 1)))
                        }
                    )
                }
            }
        }
    }
}