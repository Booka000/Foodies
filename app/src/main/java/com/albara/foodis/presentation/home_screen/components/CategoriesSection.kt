package com.albara.foodis.presentation.home_screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.albara.foodis.categories.Categories
import com.albara.foodis.category.Category
import com.albara.foodis.category.Property1
import com.albara.foodis.domain.modal.Category
import com.albara.foodis.presentation.home_screen.HomeScreenEvent

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    categories : List<Category>,
    selectedCategoryId : Int,
    onEvent : (HomeScreenEvent) -> Unit
) {
    Categories(
        modifier = modifier,
        items = {
            categories.onEachIndexed{index , category ->
                Category(
                    categoryName = category.name,
                    onClick = {
                        onEvent(HomeScreenEvent.UpdateSelectedCategoryIndex(index))
                    },
                    property1 = if (selectedCategoryId == category.id) Property1.On
                    else Property1.Off,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        categoriesBackgroundColor = MaterialTheme.colorScheme.background
    )
}