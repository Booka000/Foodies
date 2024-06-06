package com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.dp
import com.albara.foodis.R
import com.albara.foodis.buttonbasic.ButtonBasic
import com.albara.foodis.buttonbasic.IconLeft
import com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components.CategoriesSection
import com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components.ItemCard
import com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components.ModalBottomSheetComponent
import com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components.SearchComponent
import com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components.TopLineComponent
import com.albara.foodis.presentation.products_list_and_product_details_screens.shared.SharedEvent
import com.albara.foodis.presentation.products_list_and_product_details_screens.shared.SharedState

@Composable
fun HomeScreen(
    state: SharedState,
    onEvent: (SharedEvent) -> Unit,
    onNavigate : () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            AnimatedContent(
                targetState = state.isSearching,
                modifier = Modifier.fillMaxWidth(),
                content = { isVisible ->
                    if (isVisible) {
                        SearchComponent { event ->
                            onEvent(event)
                        }
                    } else {
                        Column {
                            TopLineComponent(
                                indicator = state.selectedTagsIndicator
                            ) { event ->
                                onEvent(event)
                            }
                            CategoriesSection(
                                categories = state.categories,
                            ) { event ->
                                onEvent(event)
                            }
                        }
                    }
                },
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            )
            Spacer(modifier = Modifier.size(5.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 5.dp,
                    end = 5.dp,
                    top = 5.dp,
                    bottom = when (state.totalInCart) {
                        0 -> 5.dp
                        else -> 100.dp
                    },
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding()
            ) {
                items(state.products) { product ->
                    ItemCard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)
                            .fillMaxHeight(),
                        product = product){ event ->
                        onEvent(event)
                        if(event is SharedEvent.UpdateSelectedProductId)
                            onNavigate()
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = state.totalInCart != 0,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ButtonBasic(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    iconLeft = IconLeft.On,
                    text = "${state.totalInCart} ₽",
                    textColor = Color.White,
                    cartIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        }
        ModalBottomSheetComponent(
            tags = state.tags,
            isVisible = state.isEditingFilters,
            onEvent = onEvent
        )
        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}