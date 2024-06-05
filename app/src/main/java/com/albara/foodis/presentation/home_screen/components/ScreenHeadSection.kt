package com.albara.foodis.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import com.albara.foodis.R
import com.albara.foodis.buttonfilter.ButtonFilter
import com.albara.foodis.buttonfilter.Indicator
import com.albara.foodis.presentation.home_screen.HomeScreenEvent
import com.albara.foodis.presentation.ui.theme.Orange
import com.albara.foodis.search.Search
import com.albara.foodis.search.Typing
import com.albara.foodis.topline.Topline

@Composable
fun TopLineComponent(
    modifier: Modifier = Modifier,
    indicator: Int,
    onEvent : (HomeScreenEvent) -> Unit
) {
    Topline(
        modifier = modifier,
        toplineBackgroundColor = MaterialTheme.colorScheme.background,
        filterIcon = {
            ButtonFilter(
                indicator = when (indicator) {
                    0 -> Indicator.Off
                    else -> Indicator.On
                },
                indicatorNumber = indicator.toString(),
                indicatorNumberColor = Color.White,
                indicatorBackgroundColor = Orange,
                filterIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = null
                    )
                },
                onClick = {
                    onEvent(HomeScreenEvent.OpenBottomSheet)
                }
            )
        },
        logo = {
            Image(
                painter = painterResource(id = R.drawable.topline_logo),
                contentDescription = null,
                modifier = Modifier.boxAlign(
                    Alignment.Center,
                    DpOffset.Zero
                )
            )
        },
        searchIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null
            )
        },
        onFilterClick = {
            onEvent(HomeScreenEvent.OpenBottomSheet)
        },
        onSearchClick = {
            onEvent(HomeScreenEvent.ShowSearchComponent)
        }

    )
}

@Composable
fun SearchComponent(
    modifier: Modifier = Modifier,
    onEvent : (HomeScreenEvent) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Search(
        modifier = modifier,
        typing = Typing.On,
        arrowLeftIcon = {
            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = null,
                tint = Orange,
            )
        },
        textfiled = {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onEvent(HomeScreenEvent.IsSearching(text))
                },
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Orange,
                    focusedIndicatorColor = Orange,
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Найти блюдо")
                }
            )
        },
        cancelIcon = {
            if(text.isNotEmpty())
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = null,
                )
        },
        onCancelClick = {
            text = ""
            onEvent(HomeScreenEvent.IsSearching(""))
        },
        onArrowLeftClick = {
            onEvent(HomeScreenEvent.HideSearchComponent)
        },
        backgroundColor = Color.Transparent
    )
}