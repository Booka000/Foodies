package com.albara.foodis.presentation.products_list_and_product_details_screens.home_screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.albara.foodis.categories.Categories
import com.albara.foodis.category.Category
import com.albara.foodis.category.Property1
import com.albara.foodis.domain.modal.Category
import com.albara.foodis.presentation.products_list_and_product_details_screens.shared.SharedEvent

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    categories : List<Category>,
    onEvent : (SharedEvent) -> Unit
) {
    var selectedCategoryIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Categories(
        modifier = modifier,
        items = {
            categories.onEachIndexed{index , category ->
                Category(
                    categoryName = category.name,
                    onClick = {
                        selectedCategoryIndex = index
                        onEvent(SharedEvent.UpdateSelectedCategoryIndex(index))
                    },
                    property1 = if (selectedCategoryIndex == index) Property1.On
                    else Property1.Off,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        categoriesBackgroundColor = MaterialTheme.colorScheme.background
    )
}