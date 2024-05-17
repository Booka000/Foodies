package com.albara.foodis.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.albara.foodis.buttonbasic.ButtonBasic
import com.albara.foodis.domain.modal.Tag
import com.albara.foodis.filter.Filter
import com.albara.foodis.modalfilter.ModalFilter
import com.albara.foodis.presentation.home_screen.HomeScreenEvent
import com.albara.foodis.presentation.ui.theme.Orange
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetComponent(
    modifier: Modifier = Modifier,
    tags : List<Tag>,
    isVisible : Boolean,
    onEvent : (HomeScreenEvent) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    if (isVisible) {
        val mutableTags = remember {
            tags.toMutableStateList()
        }
        ModalBottomSheet(
            modifier = modifier,
            sheetState = modalBottomSheetState, onDismissRequest = {
                onEvent(HomeScreenEvent.CloseBottomSheet)
            },
            dragHandle = null
        ) {
            ModalFilter(
                backgroundColor = MaterialTheme.colorScheme.background,
                title = "Подобрать блюда",
                titleColor = MaterialTheme.colorScheme.onBackground,
                applyButton = {
                    ButtonBasic(
                        modifier = Modifier.fillMaxSize(),
                        text = "Готово",
                        textColor = Color.White,
                        onClick = {
                            scope.launch { modalBottomSheetState.hide() }.invokeOnCompletion {
                                if (!modalBottomSheetState.isVisible) {
                                    onEvent(HomeScreenEvent.CloseBottomSheet)
                                    onEvent(HomeScreenEvent.UpdateTags(mutableTags.filter { tag -> tag.isSelected }))
                                }
                            }
                        }
                    )
                },
                filters = {
                    mutableTags.onEachIndexed { index, tag ->
                        FilterComponent(
                            tag = tag,
                            modifier = Modifier.border(
                                Dp.Unspecified,
                                color = Color.Transparent
                            )
                        ) { isChecked ->
                            mutableTags[index] = mutableTags[index].copy(isSelected = isChecked)
                        }
                        if(index < mutableTags.size -1)
                            HorizontalDivider(modifier.fillMaxWidth())
                    }
                }
            )
        }
    }
}

@Composable
fun FilterComponent(
    modifier : Modifier = Modifier,
    tag : Tag,
    onCheckChanged : (Boolean) -> Unit
){
    Filter(
        tagName = tag.name,
        tagNameColor = MaterialTheme.colorScheme.onBackground,
        checkBox = {
            Checkbox(
                checked = tag.isSelected,
                onCheckedChange = {isSelected ->
                    onCheckChanged(isSelected)
                },
                colors = CheckboxDefaults.colors().copy(
                    checkedBoxColor = Orange,
                    checkedBorderColor = Orange,
                    checkedCheckmarkColor = Color.White
                )
            )
        },
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    )
}