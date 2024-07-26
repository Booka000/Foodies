package com.albara.foodies.presentation.cart_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.albara.foodies.R
import com.albara.foodies.buttonbasic.ButtonBasic
import com.albara.foodies.buttonbasic.IconLeft
import com.albara.foodies.presentation.cart_screen.components.CartRowComponent
import com.albara.foodies.presentation.ui.theme.Orange
import com.albara.foodies.topbarbase.TopBarBase

@Composable
fun CartScreen(
    state: CartScreenState,
    onEvent :(CartScreenEvent) -> Unit,
    onNavigateBack : () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 30.dp)) {
        Column {
            TopBarBase(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                title = "Корзина",
                backgroundColor = MaterialTheme.colorScheme.background,
                titleTextColor = MaterialTheme.colorScheme.onBackground,
                leftSideIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowleft),
                        contentDescription = null,
                        tint = Orange
                    )
                },
                onLeftSideIconClick = onNavigateBack
                )
            LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(vertical = 5.dp)) {
                items(state.productsInCart) {product ->
                    CartRowComponent(
                        modifier = Modifier.fillMaxWidth(),
                        product = product,
                        onEvent = onEvent
                    )
                }
            }
        }
        if(state.totalInCart != 0) {
            ButtonBasic(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.BottomCenter),
                iconLeft = IconLeft.Off,
                text = "Заказать за ${state.totalInCart} ₽",
                textColor = Color.White
            )
        }
    }
}