package com.albara.foodis.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.albara.foodis.R
import com.albara.foodis.buttonbasic.ButtonBasic
import com.albara.foodis.buttonbasic.IconLeft
import com.albara.foodis.categories.Categories
import com.albara.foodis.category.Category
import com.albara.foodis.category.Property1
import com.albara.foodis.presentation.home_screen.HomeScreenEvent
import com.albara.foodis.presentation.home_screen.HomeScreenViewModel
import com.albara.foodis.presentation.home_screen.components.ItemCard
import com.albara.foodis.presentation.ui.theme.FoodisTheme
import com.albara.foodis.presentation.ui.theme.RemoveRipple
import com.albara.foodis.topline.Topline
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeScreenViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodisTheme {
                val state by viewModel.state.collectAsState()
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RemoveRipple {
                        Box (contentAlignment = Alignment.Center) {
                            Column {
                                Topline(
                                    toplineBackgroundColor = MaterialTheme.colorScheme.background,
                                    filterIcon = {
                                        Icon(painter = painterResource(id = R.drawable.filter), contentDescription = null)
                                    },
                                    onFilterClick = {
                                        viewModel.onEvent(HomeScreenEvent.OpenBottomSheet)
                                    },
                                    logo = {
                                        Image(painter = painterResource(id = R.drawable.topline_logo),
                                            contentDescription = null,
                                            modifier = Modifier.boxAlign(Alignment.Center, DpOffset.Zero)
                                        )
                                    },
                                    searchIcon = {
                                        Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
                                    }
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                var selectedCategoryId by remember {
                                    mutableStateOf<Int?>(null)
                                }
                                Categories(
                                    items = {
                                        state.categories.onEach {category ->
                                            Category(
                                                categoryName = category.name,
                                                onClick = {
                                                    if (selectedCategoryId == category.id){
                                                        selectedCategoryId = null
                                                        viewModel.onEvent(HomeScreenEvent.UpdateCategoryId(null))
                                                    } else {
                                                        selectedCategoryId = category.id
                                                        viewModel.onEvent(HomeScreenEvent.UpdateCategoryId(category.id))
                                                    }
                                                },
                                                property1 = if (selectedCategoryId == category.id) Property1.On
                                                else Property1.Off,
                                                textColor = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                    },
                                    categoriesBackgroundColor = MaterialTheme.colorScheme.background
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                LazyVerticalGrid(columns = GridCells.Fixed(2),
                                    contentPadding = PaddingValues(
                                        start = 5.dp,
                                        end = 5.dp,
                                        top = 5.dp,
                                        bottom = 100.dp,
                                    ),
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding()
                                ) {
                                    items(state.products) {product ->
                                        ItemCard(modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(5.dp)
                                            .fillMaxHeight(),
                                            product = product,
                                            onEvent = viewModel::onEvent)
                                    }
                                }
                            }
                            if(state.totalInCart != 0)
                                Box (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                ) {
                                    ButtonBasic(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth()
                                            .padding(20.dp),
                                        iconLeft = IconLeft.On,
                                        text = "${state.totalInCart} ₽",
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.cart),
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        }
                                    )
                                }
                            if(state.isEditingFilters)
                                ModalBottomSheet(onDismissRequest = {
                                    viewModel.onEvent(HomeScreenEvent.CloseBottomSheet)
                                },
                                    dragHandle = null) {
                                }
                        }
                    }
                }
            }
        }
    }
}
