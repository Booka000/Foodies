package com.albara.foodis.presentation.product_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.albara.foodis.domain.modal.Product
import com.albara.foodis.listitem.ListItem
import com.albara.foodis.presentation.home_screen.HomeScreenEvent

@Composable
fun ProductDetailsScreen(
    product: Product?,
    onEvent : (HomeScreenEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        if(product == null) {
            CircularProgressIndicator()
        }
        Column {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                text = product?.name ?: "",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.size(5.dp))
            ListItem(
                backgroundColor = Color.Transparent,
                name = "Вес",
                value = "${product?.measure}${product?.measureUnit}",
                nameTextColor = MaterialTheme.colorScheme.onBackground,
                valueTextColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}